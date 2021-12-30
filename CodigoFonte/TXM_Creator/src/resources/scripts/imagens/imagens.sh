#!/bin/bash

pasta_inicio="$1/$2"

# Início Horácio - 25/05/2018 - O caminho relativo da pasta_images mudou.  Pasta_origem foi eliminada.
#pasta_origem=$(pwd | sed -e s/' '/'\ '/g -e s/'('/'\('/g -e s/')'/'\)'/g)"/Modules/arquivos-base"
#pasta_images=$(pwd | sed -e s/' '/'\ '/g -e s/'('/'\('/g -e s/')'/'\)'/g)"/Modules/imagens"
#pasta_origem=$(pwd | sed -e s/' '/'\ '/g -e s/'('/'\('/g -e s/')'/'\)'/g)
pasta_images=$(pwd | sed -e s/' '/'\ '/g -e s/'('/'\('/g -e s/')'/'\)'/g)"/imagens" 
# Fim Horácio - 25/05/2018

#echo $pasta_inicio
#echo $pasta_origem
#echo $pasta_images

cd "$pasta_inicio/Imagens-base"

#GERADOR DE IMAGENS

rm -rf "$pasta_inicio"/imagensGeradas
mkdir "$pasta_inicio"/imagensGeradas
pasta_img="$pasta_inicio"/imagensGeradas

echo " "
echo "Gerando logotipos..."
echo " "
sips -s format jpeg logo.png --out logo.jpg
if [ -f logo-taxi.png ];
then
	sips -s format jpeg logo-taxi.png --out logo-taxi.jpg;
fi

for tam in 1024 512;
do
	cp logo.jpg "$pasta_img"/logo_"$tam".jpg;
	sips -Z $tam "$pasta_img"/logo_"$tam".jpg;
if [ -f logo-taxi.png ];
then
	cp logo-taxi.jpg "$pasta_img"/logo-taxi_"$tam".jpg;
	sips -Z $tam "$pasta_img"/logo-taxi_"$tam".jpg;
fi
done

cp "$pasta_img"/logo_512.jpg "$pasta_img"/"$8".jpg

echo " "
echo "Gerando gráfico de recursos do Google Play..."
echo " "
cp googleplay.png "$pasta_img"/googleplay.png

if [ -f googleplay-taxi.png ];
then
	cp googleplay-taxi.png "$pasta_img"/googleplay-taxi.png;
fi

echo " "
echo "Gerando ícones..."
echo " "
if [ ! -f icon.png ] && [ ! -f icon-taxi.png ];
then
	cp logo.png icon.png
	if [ -f logo-taxi.png ];
	then
		cp logo-taxi.png icon-taxi.png;
	else
		cp logo.png icon-taxi.png;
	fi
fi
if [ -f icon.png ] && [ ! -f icon-taxi.png ];
then
	cp icon.png icon-taxi.png;
fi
sips -Z 290 icon*.png

for tam in 29 57 58 80 114 120 145 290;
do
	cp icon.png "$pasta_img"/"$tam"x"$tam".png;
	sips -Z $tam "$pasta_img"/"$tam"x"$tam".png;
	cp icon-taxi.png "$pasta_img"/"$tam"x"$tam"-taxi.png;
	sips -Z $tam "$pasta_img"/"$tam"x"$tam"-taxi.png;
done

echo " "
echo "Gerando ícones redondos..."
echo " "
if [ ! -f icon-rounded.png ];
then
	convert icon.png "$pasta_images"/maskicon.png -alpha Off -compose CopyOpacity -composite icon-rounded.png;
fi
if [ ! -f icon-rounded-taxi.png ];
then
	convert icon-taxi.png "$pasta_images"/maskicon.png -alpha Off -compose CopyOpacity -composite icon-rounded-taxi.png;
fi

for tam in 48 58 72 96 120;
do
	cp icon-rounded.png "$pasta_img"/"$tam"x"$tam"r.png;
	sips -Z $tam "$pasta_img"/"$tam"x"$tam"r.png;
	cp icon-rounded-taxi.png "$pasta_img"/"$tam"x"$tam"r-taxi.png;
	sips -Z $tam "$pasta_img"/"$tam"x"$tam"r-taxi.png;
done

echo " "
echo "Gerando barras de navegação..."
echo " "
if [ -f navbar-fa.png ];
then
	if [ ! -f navbar-fp.png ];
	then
		cp navbar-fa.png navbar-fp.png;
	fi
	for tam in "648x228" "432x152" "262x92" "216x76" "108x38" "128x45";
	do
		cp navbar-fa.png "$pasta_img"/"$tam"fa.png && cp navbar-fp.png "$pasta_img"/"$tam"fp.png;
	done
elif [ -f navbar.png ];
then
	if [ ! -f navbar-taxi.png ];
	then
		cp navbar.png navbar-taxi.png;
	fi
	for tam in "648x228" "432x152" "262x92" "216x76" "108x38" "128x45";
	do
		cp navbar.png "$pasta_img"/"$tam"fa.png && cp navbar-taxi.png "$pasta_img"/"$tam"fp.png;
	done
fi
rm "$pasta_img"/128x45fp.png
sips -Z 648 "$pasta_img"/648x228fa.png "$pasta_img"/648x228fp.png
sips -Z 432 "$pasta_img"/432x152fa.png "$pasta_img"/432x152fp.png
sips -Z 262 "$pasta_img"/262x92fa.png "$pasta_img"/262x92fp.png
sips -Z 216 "$pasta_img"/216x76fa.png "$pasta_img"/216x76fp.png
sips -Z 108 "$pasta_img"/108x38fa.png "$pasta_img"/108x38fp.png
sips -Z 128 "$pasta_img"/128x45fa.png
mv "$pasta_img"/128x45fa.png "$pasta_img"/logo-bandeira.png

echo " "
echo "Gerando splash..."
echo " "

for tam in "720x1280" "640x1136";
do
	cp splash.png "$pasta_img"/"$tam".png;
	if [ ! -f splash-taxi.png ];
	then
		cp splash.png splash-taxi.png;
	fi
	cp splash-taxi.png "$pasta_img"/"$tam"-taxi.png;
done

if [ ! -f splash2.png ];
then
	cp splash.png splash2.png;
	sips -c 1080 720 splash2.png;
fi

if [ ! -f splash3.png ];
then
	cp splash.png splash3.png;
	sips -c 1200 720 splash3.png;
fi

if [ ! -f splash4.png ];
then
	cp splash.png splash4.png;
	sips -c 1152 720 splash4.png;
fi

if [ ! -f splash5.png ];
then
	cp splash.png splash5.png;
	sips -c 881 720 splash5.png;
fi

if [ ! -f splash2-taxi.png ];
then
	cp splash-taxi.png splash2-taxi.png;
	sips -c 1080 720 splash2-taxi.png;
fi

if [ ! -f splash3-taxi.png ];
then
	cp splash-taxi.png splash3-taxi.png;
	sips -c 1200 720 splash3-taxi.png;
fi

if [ ! -f splash4-taxi.png ];
then
	cp splash-taxi.png splash4-taxi.png;
	sips -c 1152 720 splash4-taxi.png;
fi

for tam in "320x480" "640x960" "318x343" "720x1080";
do
	cp splash2.png "$pasta_img"/"$tam".png;
	cp splash2-taxi.png "$pasta_img"/"$tam"-taxi.png;
done

for tam in "480x800";
do
	cp splash3.png "$pasta_img"/"$tam".png;
	cp splash3-taxi.png "$pasta_img"/"$tam"-taxi.png;
done

for tam in "200x320";
do
	cp splash4.png "$pasta_img"/"$tam".png;
	cp splash4-taxi.png "$pasta_img"/"$tam"-taxi.png;
done

for tam in "300x367";
do
	cp splash5.png "$pasta_img"/"$tam".png;
	cp splash5-taxi.png "$pasta_img"/"$tam"-taxi.png;
done

sips -Z 367 "$pasta_img"/300x367.png;

sips -Z 1138 "$pasta_img"/640x1136*
sips -c 1136 640 "$pasta_img"/640x1136*

sips -Z 477 "$pasta_img"/318x343.png
sips -c 343 318 "$pasta_img"/318x343.png

sips -Z 480 "$pasta_img"/320x480*
sips -Z 960 "$pasta_img"/640x960*

sips -Z 800 "$pasta_img"/480x800*

sips -Z 320 "$pasta_img"/200x320*

if [ $7 == "pkg" ]; then
	cd "$pasta_inicio";
	mkdir Imagens_iOS Imagens_Android Imagens_Taxista;

	echo " ";
	echo "Gerando pasta das imagens Android...";
	echo " ";
	cd Imagens_Android;
	mkdir drawable drawable-ldpi drawable-mdpi drawable-hdpi drawable-xhdpi;
	cp "$pasta_img"/58x58r.png drawable/bglogo.png;
	cp "$pasta_img"/108x38fa.png drawable/navbarlogo.png;
	cp "$pasta_img"/108x38fp.png drawable/navbarpretalogo.png;
	cp "$pasta_img"/120x120r.png drawable/perfillogo.png;
	cp "$pasta_img"/640x1136.png drawable/splash.png;


	cp "$pasta_img"/200x320.png drawable-ldpi/splash.png;

	cp "$pasta_img"/72x72r.png drawable-hdpi/ic_launcher.png;
	cp "$pasta_img"/480x800.png drawable-hdpi/splash.png;

	cp "$pasta_img"/48x48r.png drawable-mdpi/ic_launcher.png;
	cp "$pasta_img"/320x480.png drawable-mdpi/splash.png;

	cp "$pasta_img"/96x96r.png drawable-xhdpi/ic_launcher.png;
	cp "$pasta_img"/720x1280.png drawable-xhdpi/splash.png;

	if [ $6 == "driver" ]; then
		cp "$pasta_images"/"$6"/powered_by@2x.png drawable/powered_by.png;
		cp "$pasta_images"/"$6"/powered_by@2x.png drawable-hdpi/powered_by.png;
		cp "$pasta_images"/"$6"/powered_by.png drawable-mdpi/powered_by.png;
	fi

	cp -R drawable drawable-mdpi drawable-hdpi drawable-xhdpi drawable-ldpi ../../Android/CoopAndroid/coop-modelo_cliente_custom/res;

	echo " ";
	echo "Gerando pasta das imagens Taxista...";
	echo " ";
	cd ../Imagens_Taxista;
	mkdir drawable drawable-ldpi drawable-mdpi drawable-hdpi drawable-xhdpi;
	cp "$pasta_img"/58x58r-taxi.png drawable/bglogo.png;
	cp "$pasta_img"/108x38fa.png drawable/navbarlogo.png;
	cp "$pasta_img"/108x38fp.png drawable/navbarpretalogo.png;
	cp "$pasta_img"/120x120r-taxi.png drawable/perfillogo.png;
	cp "$pasta_img"/640x1136-taxi.png drawable/splash.png;

	cp "$pasta_img"/200x320-taxi.png drawable-ldpi/splash.png;

	cp "$pasta_img"/72x72r-taxi.png drawable-hdpi/ic_launcher.png;
	cp "$pasta_img"/480x800-taxi.png drawable-hdpi/splash.png;

	cp "$pasta_img"/48x48r-taxi.png drawable-mdpi/ic_launcher.png;
	cp "$pasta_img"/320x480-taxi.png drawable-mdpi/splash.png;

	cp "$pasta_img"/96x96r-taxi.png drawable-xhdpi/ic_launcher.png;
	cp "$pasta_img"/720x1280-taxi.png drawable-xhdpi/splash.png;

	if [ $6 == 'driver' ]; then
		cp "$pasta_images"/"$6"/powered_by@2x.png drawable/powered_by.png;
		cp "$pasta_images"/"$6"/powered_by@2x.png drawable-hdpi/powered_by.png;
		cp "$pasta_images"/"$6"/powered_by.png drawable-mdpi/powered_by.png;
	fi

	cp -R drawable drawable-mdpi drawable-hdpi drawable-xhdpi drawable-ldpi ../../Android/CoopAndroid/coop-modelo_taxista_custom/res;


	echo " ";
	echo "Gerando pasta das imagens iOS...";
	echo " ";
	cd ../Imagens_iOS;
	mkdir NavImage ProfilImage PoweredByImages LaunchAssets.xcassets LaunchAssets.xcassets/AppIcon.appiconset LaunchAssets.xcassets/LaunchImage.launchimage;
	cp "$pasta_img"/29x29.png Spotlight.png;
	cp "$pasta_img"/58x58.png LaunchAssets.xcassets/AppIcon.appiconset/Icon-29@2x.png;
	cp "$pasta_img"/80x80.png LaunchAssets.xcassets/AppIcon.appiconset/Icon-40@2x.png;
	cp "$pasta_img"/120x120.png LaunchAssets.xcassets/AppIcon.appiconset/Icon-60@2x.png;
	cp "$pasta_img"/logo_1024.jpg LaunchAssets.xcassets/AppIcon.appiconset/AppStore-1024.png;
	cp "$pasta_img"/57x57.png Icon.png;
	cp "$pasta_img"/114x114.png Icon@2x.png;

	cp "$pasta_img"/108x38fa.png NavImage/navbar-logo.png;
	cp "$pasta_img"/216x76fa.png NavImage/navbar-logo@2x.png;

	cp "$pasta_img"/145x145.png ProfilImage/perfil-logo.png;
	cp "$pasta_img"/290x290.png ProfilImage/perfil-logo@2x.png;

	#echo '==================== EH AQUIIIIII ===================';
	cp "$pasta_images"/"$6"/powered_by@2x.png PoweredByImages/powered_by@2x.png;
	cp "$pasta_images"/"$6"/powered_by.png PoweredByImages/powered_by.png;
	#echo '==================== FOI AQUIIIIII ==================';

	cp "$pasta_img"/320x480.png LaunchAssets.xcassets/LaunchImage.launchimage/Default.png;
	cp "$pasta_img"/640x960.png LaunchAssets.xcassets/LaunchImage.launchimage/Default@2x.png;
	cp "$pasta_img"/640x1136.png LaunchAssets.xcassets/LaunchImage.launchimage/Default-568h@2x.png;

	cp -R *.png NavImage ProfilImage PoweredByImages LaunchAssets.xcassets ../../iOS/coopios/TaxiMachine/Resources;

	cd ..;
	rm -r Imagens_iOS Imagens_Android Imagens_Taxista;
fi

echo "-------------------------"
echo "Fim da edição de imagens."
