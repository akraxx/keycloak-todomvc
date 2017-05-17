package commands

import (
	"net/http"
	"log"
	"api"
	"encoding/json"
)

func List(apiUri string) {
	resp, err := http.Get(apiUri + "todos")

	if err != nil {
		log.Fatalf("Unable to contact remote server (%s) : %s", apiUri, err)
	}

	defer resp.Body.Close()

	if resp.StatusCode != 200 {
		log.Fatalf("Bad response from server : %s (%d)", resp.Status, resp.StatusCode)
	}

	todosResponse := new(api.TodosResponse)
	json.NewDecoder(resp.Body).Decode(todosResponse)

	log.Printf("You have %d todo(s) :", todosResponse.Page.TotalElements)

	for _, todo := range todosResponse.Embedded.Todos {
		if(todo.Completed) {
			log.Printf("[x] %s", todo.Title)
		} else {
			log.Printf("[ ] %s", todo.Title)
		}
	}
}