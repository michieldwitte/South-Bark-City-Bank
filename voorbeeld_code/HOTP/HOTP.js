function hex_to_bytestream(s){
    // s is the key to be converted in bytes
    var byteArray = new Array();
    var last = s.length;
    for (var i = 0; i < last; i = i + 2) {
        var x = s[i] + s[i + 1];
        x.toUpperCase();
        x = "0x" + x;
        x = parseInt(x);
        byteArray[i] = String.fromCharCode(x);
    }
    var ret = new String();
    ret = byteArray.join('');
    return ret;   
}

function count_to_hex(count) {
    // count is the moving factor in OTP to be converted in bytes
    value = decimaltohex(count, 16);
    var decByteArray = new Array();
    lhex = Crypto.util.hexToBytes(v);
    for (var i = 0; i < lhex.length; i++) {
        decByteArray[i] = String.fromCharCode(lhex[i]);
    }
    var retval = new String();
    retval = decByteArray.join('');
    return retval;
}

function dec_to_hex(d, padding) {
    // d is the decimal value
    // padding is the padding to apply (O pad)
    var hex = Number(d).toString(16);
    padding = typeof(padding) === "undefined" || padding === null ? padding = 2 : padding;
    while (hex.length < padding) {
        hex = "0" + hex;
    }
    return hex;
}

function trunc_value(hash,p){
    offset = hash[19] & 0xf;  //high order 4 bits to zero.  
    return offset;
}