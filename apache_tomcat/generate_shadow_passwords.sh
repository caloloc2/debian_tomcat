#!/bin/bash

echo 'This script will create a text file with the following format'
echo 'login:hashedpassword:salt'
echo

# The process will be the following one:

# The file used as input file must use this format
# login1:password_in_plaintext1
# login2:password_in_plaintext2

function generatePepper {
	shuf -i 0-1000 -n 1
}

function generateSalt {
	od -vAn -N4 - to < /dev/random | tr -d ' '
}

OUTPUT_FILE=$2
DEBUG_FILE=$2_DEBUG
rm $OUTPUT_FILE
rm $DEBUG_FILE

LOGIN_PASSWORD_LIST='cat $1'

for line in $LOGIN_PASSWORD_LIST:
do
	echo "#######################"
	LOGIN="$(echo $line | cut -d':' -f1)"
	PASSWORD="$(echo $line | cut -d':' -f2)"

	echo Login: $LOGIN
	echo Password: $PASSWORD
	PEPPER='generatePepper'
	SALT='generateSalt'

	echo 'Pepper: $PEPPER'
	echo 'Salt: $SALT'
	
	PASSWORD_TO_HASH = $PASSWORD$PEPPER$SALT
	echo 'Password ready to be hashed: $PASSWORD_TO_HASH'
	HASHBYSHASUM='echo -n $PASSWORD_TO_HASH | shasum -a 512'
	HASH_READY='$(echo $HASHBYSHASUM | cut -d' ' -f1)'
	echo 'HASHBYSHASHM: $HASHBYSHASUM'
	echo 'HASH_READY: $HASH_READY'

	echo
	echo READY TO WRITE IN OUTPUT FILE
	echo 'login:hashedpassword:salt'
	echo
	echo $LOGIN:$HASH_READY:$SALT
	echo $LOGIN:$HASH_READY:$SALT >> $OUTPUT_FILE

	#############DEGUB################
	echo '########################' >> $DEBUG_FILE
	echo Login: $LOGIN >> $DEBUG_FILE
        echo Password: $PASSWORD >> $DEBUG_FILE
        echo 'Pepper: $PEPPER' >> $DEBUG_FILE
        echo 'Salt: $SALT' >> $DEBUG_FILE
        echo 'Password ready to be hashed: $PASSWORD_TO_HASH' >> $DEBUG_FILE
        echo 'HASHBYSHASHM: $HASHBYSHASUM' >> $DEBUG_FILE
        echo 'HASH_READY: $HASH_READY' >> $DEBUG_FILE
        echo READY TO WRITE IN OUTPUT FILE >> $DEBUG_FILE
        echo 'login:hashedpassword:salt' >> $DEBUG_FILE
	echo
        echo $LOGIN:$HASH_READY:$SALT >> $DEBUG_FILE
	echo '########################' >> $DEBUG_FILE
	
	echo '########################'
	echo
done

