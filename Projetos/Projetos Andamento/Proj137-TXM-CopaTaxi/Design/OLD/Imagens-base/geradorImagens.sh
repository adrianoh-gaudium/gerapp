#!/bin/bash

pasta_raiz="$(pwd)"
pasta_design="${pasta_raiz%/*}"
mkdir "$pasta_design"/imagensGeradas
pasta_img="$pasta_design"/imagensGeradas

#logo
for tam in 1024 512; do cp logo.png "$pasta_img"/"$tam"x"$tam".png; done
for tam in 512; do sips -Z $tam "$pasta_img"/"$tam"x"$tam".png; done

#googleplay
cp googleplay.png "$pasta_img"/1024x500.png

#icon
for tam in 29 36 48 57 58 72 80 96 114 120 145 290; do cp icon.png "$pasta_img"/"$tam"x"$tam".png; done
for tam in 29 36 48 57 58 72 80 96 114 120 145; do sips -Z $tam "$pasta_img"/"$tam"x"$tam".png; done

#navbar-fa e navbar-fp
for tam in "216x76" "108x38"; do cp navbar-fa.png "$pasta_img"/"$tam"fa.png && cp navbar-fp.png "$pasta_img"/"$tam"fp.png; done
sips -Z 108 "$pasta_img"/108x38fa.png "$pasta_img"/108x38fp.png

#splash
for tam in "720x1280" "640x1136"; do cp splash.png "$pasta_img"/"$tam".png; done
sips -Z 1138 "$pasta_img"/640x1136.png
sips -c 1136 640 "$pasta_img"/640x1136.png
cp "$pasta_img"/640x1136.png "$pasta_img"/640x960.png
sips -c 960 640 "$pasta_img"/640x960.png
cp "$pasta_img"/640x960.png "$pasta_img"/320x480.png
sips -Z 480 "$pasta_img"/320x480.png

cd "$pasta_design"
mkdir Imagens_iOS Imagens_Android

cd Imagens_Android
mkdir drawable drawable-mdpi drawable-hdpi drawable-xhdpi
cp "$pasta_img"/58x58.png drawable/bglogo.png
cp "$pasta_img"/108x38fa.png drawable/navbarlogo.png
cp "$pasta_img"/108x38fp.png drawable/navbarpretalogo.png
cp "$pasta_img"/120x120.png drawable/perfillogo.png
cp "$pasta_img"/640x1136.png drawable/splash.png

cp "$pasta_img"/72x72.png drawable-hdpi/ic_launcher.png
cp "$pasta_img"/640x1136.png drawable-hdpi/splash.png

cp "$pasta_img"/48x48.png drawable-mdpi/ic_launcher.png
cp "$pasta_img"/640x1136.png drawable-mdpi/splash.png

cp "$pasta_img"/96x96.png drawable-xhdpi/ic_launcher.png
cp "$pasta_img"/640x1136.png drawable-xhdpi/splash.png

cd ../Imagens_iOS
mkdir NavImage ProfilImage
cp "$pasta_img"/29x29.png Spotlight.png
cp "$pasta_img"/58x58.png Spotlight@2x.png
cp "$pasta_img"/80x80.png Spotlight-40@2x.png
cp "$pasta_img"/57x57.png Icon.png
cp "$pasta_img"/114x114.png Icon@2x.png
cp "$pasta_img"/120x120.png Icon-60@2x.png

cp "$pasta_img"/108x38fa.png NavImage/navbar-logo.png
cp "$pasta_img"/216x76fa.png NavImage/navbar-logo@2x.png

cp "$pasta_img"/145x145.png ProfilImage/perfil-logo.png
cp "$pasta_img"/290x290.png ProfilImage/perfil-logo@2x.png

cp "$pasta_img"/320x480.png Default.png
cp "$pasta_img"/640x960.png Default@2x.png
cp "$pasta_img"/640x1136.png Default-568h@2x.png