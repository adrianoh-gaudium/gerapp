import firebase_admin
from firebase_admin import credentials, project_management
import requests

# cred = credentials.Certificate("/Users/Adrianoh/Desktop/mchapp-tools/new_scripts/machine-apps-1-firebase-adminsdk-p9g3p-77482d8a07.json")
# firebase_admin.initialize_app(cred)

# package_name = "com.google.test"
# display_name = "TestingTest"
# app = project_management.create_android_app(package_name, display_name)
# print(app.id)

# response = requests.get("https://omegadev.taximachine.com.br/api/geracao/obterImagens", params={"cod_interno": "200"}, headers={"app-key": "chCoopTXMExemplo"})
# resp_json = response.json()
# print(resp_json)
# url = resp_json['response']['logo-bandeira']
# print(url)
# img_resp = requests.get(resp_json['response']['logo-bandeira'])

# file = open("sample_image.jpg", "wb")
# file.write(img_resp.content)
# file.close()
# print(resp_json)

response = requests.get("https://omegadev.taximachine.com.br/api/geracao/obterInformacaoProjeto", params={"cod_interno": "9729"}, headers={"app-key": "chCoopTXMExemplo"})
resp_json = response.json()['response']
print(resp_json['nome'])


