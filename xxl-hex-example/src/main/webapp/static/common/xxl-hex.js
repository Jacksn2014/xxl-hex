/**
 *
 * dependency jquery
 * Created by xuxueli on 16/10/5.
 */

// --------------------------------- hex util ---------------------------------
var HexClient = {
    handle: function(BASE_URL, mapping, request_data){
        var request_json = JSON.stringify( request_data );

        // json 2 byte
        len = get4Len(getStrLen(request_json));

        var requsetWriter = new ByteWriteFactory();
        requsetWriter.writeInt(len);
        requsetWriter.writeString(request_json, len)

        var requestBytes = requsetWriter.bytes;

        // byte 2 hex
        var request_hex = bytesToHex(requestBytes);

        var url = BASE_URL + "?mapping=" + mapping + "&hex=" + request_hex;
        var response_hex;
        $.ajax({
            url: url,
            async: false,
            success : function (data) {
                response_hex =  data;
                if (response_hex) {
                    response_hex = response_hex.trim();
                }
            }
        });
        if (response_hex) {
            console.log("response_hex:");
            console.log(response_hex);

            // hex 2 byte
            var responseReader = new ByteReadFactory();
            responseReader.init(response_hex)

            // byte 2 json
            var resp_len = responseReader.readInt();
            var response_json = responseReader.readString(resp_len);
            response_json = response_json.trim();

            if (response_json && response_json.indexOf("{")>-1) {
                var response = JSON.parse( response_json );
                return response;
            }
        }
        return {'code':500, 'msg':'请求失败'};
    }
}

// --------------------------------- ByteReadFactory ---------------------------------
function ByteReadFactory() {
    this.offset = 0;
    this.response_byte;
    this.init = function (response_hex) {
        this.response_byte = hexToBytes(response_hex);
    }
    this.readInt = function () {
        if (this.offset + 4 > this.response_byte.length) {
            return 0;
        }
        var intValue = (this.response_byte[this.offset] & 0xff)
            | ((this.response_byte[this.offset + 1] & 0xff) << 8)
            | ((this.response_byte[this.offset + 2] & 0xff) << 16)
            | ((this.response_byte[this.offset + 3] & 0xff) << 24);
        this.offset += 4;
        return intValue;
    }
    this.readString = function (length) {
        if (this.offset + length > this.response_byte.length) {
            console.log("[byte stream factory read string length error.]");
            return "";
        }

        var result = "";
        for (var i = 0; i < length; i++) {
            //result += String.fromCharCode( parseInt( this.response_byte[this.offset + i] , 2) )
            var item = String.fromCharCode( this.response_byte[this.offset + i] );

            if (i >= length-1) {
                console.log(">>>"+ item );
                console.log(">>>"+ item.length );
                console.log(">>>"+ (item.replace(" ", "")).length );
            } else {
                result += item;
            }
        }

        this.offset += length;
        return result;
    }
}

// --------------------------------- ByteWriteFactory ---------------------------------
function ByteWriteFactory(){
    this.bytes  = new Array();
    this.writeInt = function (intValue) {
        var intBytes = new Array(4);
        for (var index = 0; index < 4; index++) {
            intBytes[index] = intValue >>> (index * 8);
        }
        this.bytes = this.bytes.concat(intBytes);
    };
    this.writeString = function (strValue, length) {
        var utf8 = [];
        for (var i=0; i < strValue.length; i++) {
            var charcode = strValue.charCodeAt(i);
            if (charcode < 0x80) {
                utf8.push(charcode);
            } else if (charcode < 0x800) {
                utf8.push(0xc0 | (charcode >> 6),
                    0x80 | (charcode & 0x3f));
            } else if (charcode < 0xd800 || charcode >= 0xe000) {
                utf8.push(0xe0 | (charcode >> 12),
                    0x80 | ((charcode>>6) & 0x3f),
                    0x80 | (charcode & 0x3f));
            } else {
                // let's keep things simple and only handle chars up to U+FFFF...
                utf8.push(0xef, 0xbf, 0xbd); // U+FFFE "replacement character"
            }
        }
        if (utf8.length < length){
            for (var i=utf8.length; i< length; i++){
                utf8.push(0x00);
            }
        }
        this.bytes = this.bytes.concat(utf8);
    }
}

// --------------------------------- len util ---------------------------------

/**
 * get len
 *
 * @param len
 * @returns {*}
 */
function get4Len(len) {
    if (len % 4 != 0) {
        // Length is best in multiples of four
        len = (Math.floor(len/4) + 1) * 4;
    }
    return len;
}

/**
 * str len
 * @param strValue
 * @returns {number}
 */
function getStrLen(strValue) {
    var len = 0;
    for (var i = 0; i < strValue.length; i++) {
        var c = strValue.charCodeAt(i);
        if(c >= parseInt("000080",16) && c <= parseInt("0007FF",16)){
            len += 2;
        }else if(c >= parseInt("000800",16) && c <= parseInt("00FFFF",16)){
            len += 3;
        }else if(c >= parseInt("010000",16) && c <= parseInt("10FFFF",16)){
            len += 4;
        }else{
            len += 1;
        }
    }
    return len;
}

// --------------------------------- hex-byte util ---------------------------------

var hex_tables = "0123456789ABCDEF";
function bytesToHex(bytes) {
    for (var hex = [], i = 0; i < bytes.length; i++) {
        hex.push(hex_tables.charAt((bytes[i] & 0xf0) >> 4));
        hex.push(hex_tables.charAt((bytes[i] & 0x0f) >> 0));
    }
    return hex.join("");
}
function hexToBytes(hex) {
    for (var bytes = [], c = 0; c < hex.length; c += 2)
        bytes.push(parseInt(hex.substr(c, 2), 16));
    return bytes;
}