import nltk
from nltk.tokenize import sent_tokenize, word_tokenize
from nltk.stem import PorterStemmer
from nltk.stem.snowball import SnowballStemmer
from flask import Flask, request, jsonify

app = Flask(__name__)
import json


@app.route('/', methods=['POST'])
def emergencia():
    palabrasClave=["accidente","atropello","asalto","bloqueo","bloquearon","robaron","robo","jalaron","fugando","fuga"
        ,"acuchillaron","bloqueando","bloquearan"]
    result=""
    cont=0
    ps = PorterStemmer()
    sSnowball_stemmer = SnowballStemmer('spanish')
    texto = request.json['twet']
    tokens = nltk.word_tokenize(texto, "spanish")
    texto_mltk=nltk.Text(tokens)
    texto_mltk.concordance("estan")
    for w in tokens:
        print(ps.stem(w))
    for i in tokens:
        cont=0
        for x in palabrasClave:
            cont=cont+1
            #print(i,x)
            if i==x:
              posicion=palabrasClave.index(x)
             # print("si esta",cont,i,x)
              verbo=palabrasClave[posicion]
              stemmers2 = sSnowball_stemmer.stem(verbo)
              return jsonify({"Categoria":stemmers2,"twet":texto})
    print()

    # for token in tokens:
    #     print(token)
    #     palabras.append(token)
    #     if token=="robo":
    #         result = "Robo"
    #         return jsonify({"Categoria": result,"twet":texto})
    # result="No es un accidente"
    # if texto_mltk.concordance("accidente"):
    #      result="Accidente"
    #      return jsonify({"Categoria":result,"twet":texto})
    # return jsonify({"Categoria":result,"twet":texto})
    #

    # texto_nltk = nltk.Text(tokens)
    # concordance_list=[]
    # concordance_list = texto_nltk.concordance("accidente")
    # if not concordance_list:
    #     print("Esto encontre", concordance_list)
    #     return " no una emergencia"
    # return "que pedo emergencia"
    result = "No es un Accidente"
    return jsonify({"Categoria":result,"twet":texto})

if __name__ == '__main__':
    app.run(debug=True)
