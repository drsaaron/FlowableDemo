#! /bin/sh

baseUrl=http://localhost:8080
workFile=/tmp/tasks.lst

# submit an article
curl -X POST -H 'Content-Type: application/json' -d '{ "author": "Scott", "url": "http://home.me" }' $baseUrl/submit

# get tasks
curl $baseUrl/tasks?assignee=editors > $workFile
echo "tasks: "
jq < $workFile

# submit a review for the first one
id=$(jq '.[0].id' $workFile)
echo "id = $id"
curl -X POST -H 'Content-Type: application/json' -d "{ \"id\": $id, \"status\": true }" $baseUrl/review

# get tasks now, the first one should be done
curl $baseUrl/tasks?assignee=editors | jq
