#!/bin/bash

for item in "$@"; do
    nomeproj=$(ls /Users/Adrianoh/Google\ Drive/Meu\ Drive/Comercial/Projetos/ | grep "Proj$item-TXM")
    nomecoop=$(echo $nomeproj | cut -d'-' -f3)
    cd /Users/Adrianoh/Google\ Drive/Meu\ Drive/Operacao/Projetos\ andamento/
    cp -R ProjXXX-TXM-Modelo $nomeproj
    echo "Olaaaa"
    echo $nomeproj

    minuscula=$(echo $nomecoop | tr '[:upper:]' '[:lower:]')

    #Generate Android Keystore and save its hash key
    bandSmall=$(echo $nomecoop | tr '[:upper:]' '[:lower:]')                  #Pega o nome da bandeira e deixa tudo minúsculo
    bandBig="$(tr '[:lower:]' '[:upper:]' <<<${bandSmall:0:1})${bandSmall:1}" #Pega o nome com tudo minúsculo, e deixa apenas a primeira letra maiúscula
    #Gera keystore
    echo y | keytool -genkeypair -dname "cn=$bandBig, ou=$bandBig, o=$bandBig, c=BR" -keyalg RSA -keysize 1024 -alias $bandSmall -keypass "$bandBig"@txm123 -keystore $nomeproj/Miscelâneas/Segurança/"$bandBig".keystore -storepass "$bandBig"@txm123 -validity 9131 #>> $nomeproj/Miscelâneas/bundle-sha1.txt

    #Obtém hash da keystore p/ Google
    #echo "Google SHA1" >> $nomeproj/Miscelâneas/bundle-sha1.txt
    googleSha=$(keytool -v -list -alias $bandSmall -keystore $nomeproj/Miscelâneas/Segurança/"$bandBig".keystore -storepass "$bandBig"@txm123 | grep "SHA1:" | cut -d' ' -f3) # >> $nomeproj/Miscelâneas/bundle-sha1.txt
    #Obtém hash da keystore p/ facebook
    #echo "Facebook Hash" >> $nomeproj/Miscelâneas/bundle-sha1.txt
    facebookHash=$(keytool -exportcert -alias $bandSmall -storepass "$bandBig"@txm123 -keystore $nomeproj/Miscelâneas/Segurança/"$bandBig".keystore | openssl sha1 -binary | openssl base64) # >> $nomeproj/Miscelâneas/bundle-sha1.txt

    #echo java -jar pepk.jar --keystore=$nomeproj/Miscelâneas/Segurança/"$bandBig".keystore --alias="$bandBig" --output=$nomeproj/Miscelâneas/Segurança/"$bandBig".zip --include-cert --encryptionkey=eb10fe8f7c7c9df715022017b00c6471f8ba8170b13049a11e6c09ffe3056a104a3bbe4ac5a955f4ba4fe93fc8cef27558a3eb9d2a529a2092761fb833b656cd48b9de6a

    java -jar /Users/Adrianoh/Google\ Drive/Meu\ Drive/Operacao/Projetos\ andamento/Proj076-TaxiMachine/Implantação/Novas\ bandeiras/scripts/pepk.jar --keystore=$nomeproj/Miscelâneas/Segurança/"$bandBig".keystore --alias="$bandSmall" --output=$nomeproj/Miscelâneas/Segurança/"$bandSmall".zip --include-cert --encryptionkey=eb10fe8f7c7c9df715022017b00c6471f8ba8170b13049a11e6c09ffe3056a104a3bbe4ac5a955f4ba4fe93fc8cef27558a3eb9d2a529a2092761fb833b656cd48b9de6a

    printdata="=============== Projeto $item ===============\n
    \n\nGoogle SHA1:
    \n"$googleSha"
    \n\nFacebook Hash:
    \n"$facebookHash"
    \n\nA chave encriptada foi salva na pasta Segurança\n
    \n-------------------------------------------\n"        
    echo -e $printdata
    echo -e $printdata >>$nomeproj/Miscelâneas/bundle-sha1.txt

done
