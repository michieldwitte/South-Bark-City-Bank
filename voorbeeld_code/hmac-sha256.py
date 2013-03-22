#!/usr/bin/env python
from hashlib import sha256

trans_table_0x5C = "".join(chr(x^0x5c) for x in xrange(256))
trans_table_0x36 = "".join(chr(x^0x36) for x in xrange(256))
blocksize = sha256().block_size

def hmac_sha256(key, msg):
	if(len(key) > blocksize):
		key = sha256(key).digest()
	key += chr(0) * (blocksize - len(key))
	outerpadding = key.translate(trans_table_0x5C)
	innerpadding = key.translate(trans_table_0x36)
	return sha256(outerpadding + sha256(innerpadding + msg).digest())

if __name__ == "__main__":
	
	h = hmac_sha256("key","sander demeester")
	print h.hexdigest()
			      
