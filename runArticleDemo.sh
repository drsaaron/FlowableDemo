#! /bin/sh

baseUrl=http://localhost:8080
workFile=/tmp/tasks.lst

# submit 2 articles
curl -X POST -H 'Content-Type: application/json' -d '{ "author": "Scott", "url": "http://home.me" }' $baseUrl/submit
curl -X POST -H 'Content-Type: application/json' -d '{ "author": "Scott", "url": "http://home2.me" }' $baseUrl/submit

# get tasks
curl $baseUrl/tasks?assignee=editors > $workFile
echo "tasks: "
jq < $workFile

# submit an approval for the first one
id=$(jq '.[0].id' $workFile)
echo "id = $id"
curl -X POST -H 'Content-Type: application/json' -d "{ \"id\": $id, \"status\": true }" $baseUrl/review

# get tasks now, the first one should be done
curl $baseUrl/tasks?assignee=editors | jq

# submit a rejection for the second one
id=$(jq '.[1].id' $workFile)
echo "id = $id"
curl -X POST -H 'Content-Type: application/json' -d "{ \"id\": $id, \"status\": false }" $baseUrl/review

# get tasks now, and both should be done
curl $baseUrl/tasks?assignee=editors | jq
