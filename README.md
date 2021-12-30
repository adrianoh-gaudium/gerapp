# mchapp-tools

Este programa, que só funciona em sistemas baseados em linux, utiliza-se de diversos shell scripts e dois utilitários que devem estar instalados preferencialmente em /opt/local/bin/, caso contrário será necessário alterar o método execScript da classe ShellExecutor.

Os dois utilitários são:
- [ImagemMagik](https://www.imagemagick.org/script/index.php)
- [wget](https://www.gnu.org/software/wget/)

Verifique na pasta `./Setup/dependencias` o script `depends.sh` para instalá-los em um computador novo.

Os scripts utilizados no programa, junto com seus respectivos diretórios para aqueles que precisam de complemento, estão na pasta `resources/scripts`, a saber:

- imagens.sh
- geraCertseChaves_new.sh
- qrcodes.sh
- flyer.sh
- appstaxibr.sh
- cartao.sh
- adesivo.sh 
- telas.sh 
- metadata.sh

O script já está configurado com o caminho relativo para cada pasta, mas caso dê erro tente trocar pelo caminho absoluto manualmente.

É necessário que o arquivo do certificado digital nomeado `txm_secret.json` ou `drm_secret.json`, usado pelo comando `supply` do [Fastlane](https://fastlane.tools), esteja na pasta principal dos scripts.  

#### Exemplo de uso do Fastlane

```sh
fastlane pem -a "br.com.taximachine.cooperativajacarei" -u viniciusoh@gaudium.com.br -p "CooperativaJacarei123" -o "/Users/horacio73/ProjetosAndamento/Proj451-TXM-CooperativaJacarei/Miscelâneas/Segurança/CertJacareiAPNSDistribution.pem"
```
