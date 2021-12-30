Este programa - que só funciona em sistemas baseados em linux - utiliza-se de diversos shell scripts e dois utilitários que devem estar instalados preferencialmente em /opt/local/bin/, caso contrário será necessário alterar o método execScript da classe ShellExecutor.

Os dois utilitários são:
ImagemMagik - https://www.imagemagick.org/script/index.php
wget - https://www.gnu.org/software/wget/

Verifique na pasta "dependencias" o script "depends.sh" para instalá-los em um computador novo.

Os scripts utilizados no programa, junto com seus respectivos diretórios para aqueles que precisam de complemento, estão na pasta "resources/scripts", a saber:

imagens.sh
geraCertseChaves_new.sh
qrcodes.sh
flyer.sh
appstaxibr.sh
cartao.sh
adesivo.sh 
telas.sh 
metadata.sh

O usuário tem que apontar o subdiretório onde eles estão, não foi possível fazê-los funcionarem diretamente do ".jar".

É necessário que o arquivo do certificado digital nomeado *_secret.json, usado pelo comando "supply" do fastlane, esteja na pasta principal dos scripts.  

#Exemplo de uso do fastlane
#fastlane pem -a "br.com.taximachine.cooperativajacarei" -u viniciusoh@gaudium.com.br -p "CooperativaJacarei123" -o "/Users/horacio73/ProjetosAndamento/Proj451-TXM-CooperativaJacarei/Miscelâneas/Segurança/CertJacareiAPNSDistribution.pem"


 
