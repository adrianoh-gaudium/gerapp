#!/bin/bash

#RODAR SCRIPT ANTES DE GERAR EM UMA MAQUINA NOVA

/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
brew install wget imagemagick
#brew install caskroom/cask/brew-cask 2> /dev/null
#brew cask install cocoadialog
sudo easy_install pip
sudo pip install httplib2 dolt

brew install findutils
perl imagemagick_type_gen > /usr/local/etc/ImageMagick-6/type.xml

#git clone git://github.com/podio/podio-py.git
