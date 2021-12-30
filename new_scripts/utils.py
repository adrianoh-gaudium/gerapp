def number_to_end(word):
    num_str = ""
    end_str = ""

    for letter in word:
        if letter.isdigit():
            num_str += letter
        else:
            end_str += letter

    return end_str + num_str

def get_proj_info_test_data():
    return {
        'response': {
            'nome': "Meu2##AppTeste",
            'tipo_bandeira': "Driver"
        }
    }