package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

type girl struct {
	Name    string
	Car     string
	Country string
}

type girls []girl

func main() {
	http.HandleFunc("/", func(res http.ResponseWriter, req *http.Request) {
		res.Write([]byte("GOLANG"))
	})

	http.HandleFunc("/getstring", func(res http.ResponseWriter, req *http.Request) {
		if req.Method == "POST" {
			bodyTemp, _ := ioutil.ReadAll(req.Body)
			fmt.Println(string(bodyTemp))
		}

	})

	http.HandleFunc("/sendjson", func(res http.ResponseWriter, req *http.Request) {
		if req.Method == "POST" {
			json.NewEncoder(res).Encode(&girl{Name: "Isabel", Car: "BMW", Country: "Spain"})
		}

	})

	http.HandleFunc("/getjson", func(res http.ResponseWriter, req *http.Request) {
		if req.Method == "POST" {
			var girlTemp girl
			json.NewDecoder(req.Body).Decode(&girlTemp)
			fmt.Println(girlTemp.Name)
			fmt.Println(girlTemp.Car)
			fmt.Println(girlTemp.Country)
		}

	})

	http.HandleFunc("/sendjsonarray", func(res http.ResponseWriter, req *http.Request) {
		if req.Method == "POST" {
			varTemp := girls{
				girl{Name: "Cristina", Car: "Seat", Country: "Germany"},
				girl{Name: "Raquel", Car: "Renault", Country: "Italy"},
			}

			json.NewEncoder(res).Encode(varTemp)

		}

	})

	http.HandleFunc("/getjsonarray", func(res http.ResponseWriter, req *http.Request) {
		if req.Method == "POST" {
			var girlsTemp girls
			json.NewDecoder(req.Body).Decode(&girlsTemp)
			for _, v := range girlsTemp {
				fmt.Println(v.Name)
				fmt.Println(v.Car)
				fmt.Println(v.Country)
				fmt.Println()
			}
		}

	})

	http.ListenAndServe(":90", nil)

}
