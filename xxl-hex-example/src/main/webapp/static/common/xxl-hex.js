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
            result += String.fromCharCode( parseInt( this.response_byte[this.offset + i] , 2) )
            //console.log("readString: " + result);
        }
        this.offset += length;

        console.log("result: " + result);
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
        var strBytes = new Array();
        for (var i = 0; i < strValue.length; i++) {
            var c = strValue.charCodeAt(i);
            var s = parseInt(c).toString(2);
            if(c >= parseInt("000080",16) && c <= parseInt("0007FF",16)){
                var af = "";
                for(var j = 0; j < (11 - s.length); j++){
                    af += "0";
                }
                af += s;
                var n1 = parseInt("110" + af.substring(0,5),2);
                var n2 = parseInt("110" + af.substring(5),2);
                if(n1 > 127) n1 -= 256;
                if(n2 > 127) n2 -= 256;
                strBytes.push(n1);
                strBytes.push(n2);
            }else if(c >= parseInt("000800",16) && c <= parseInt("00FFFF",16)){
                var af = "";
                for(var j = 0; j < (16 - s.length); j++){
                    af += "0";
                }
                af += s;
                var n1 = parseInt("1110" + af.substring(0,4),2);
                var n2 = parseInt("10" + af.substring(4,10),2);
                var n3 = parseInt("10" + af.substring(10),2);
                if(n1 > 127) n1 -= 256;
                if(n2 > 127) n2 -= 256;
                if(n3 > 127) n3 -= 256;
                strBytes.push(n1);
                strBytes.push(n2);
                strBytes.push(n3);
            }else if(c >= parseInt("010000",16) && c <= parseInt("10FFFF",16)){
                var af = "";
                for(var j = 0; j < (21 - s.length); j++){
                    af += "0";
                }
                af += s;
                var n1 = parseInt("11110" + af.substring(0,3),2);
                var n2 = parseInt("10" + af.substring(3,9),2);
                var n3 = parseInt("10" + af.substring(9,15),2);
                var n4 = parseInt("10" + af.substring(15),2);
                if(n1 > 127) n1 -= 256;
                if(n2 > 127) n2 -= 256;
                if(n3 > 127) n3 -= 256;
                if(n4 > 127) n4 -= 256;
                strBytes.push(n1);
                strBytes.push(n2);
                strBytes.push(n3);
                strBytes.push(n4);
            }else{
                strBytes.push(c & 0xff);
            }
        }

        if (strBytes.length < length){
            for (var i=strBytes.length; i< length; i++){
                strBytes.push(0x00);
            }
        }

        this.bytes = this.bytes.concat(strBytes);
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