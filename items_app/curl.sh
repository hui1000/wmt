curl -H 'Content-Type: application/json' \
     -H 'Accept: application/json' \
     -H 'X-Auth-Token: 45f20996-e58d-4245-9748-2e0bbeb3d80f|wmt_user1' \
     -v   \
     -d '{ "items":[ 
     { "description": "d3" },
     {"description": "Item 3"}    
     ]}' \
     http://localhost:7340/services/items


echo  "******111 ***\n"

curl -H 'Accept: application/json'  \
     -H 'X-Auth-Token: 8ed40f12-5003-4992-a4d7-85de9ecb0e5b|wmt_user1' \
     -v \
    http://localhost:7340/services/items/1

echo  "******222 ***\n"


#curl -H 'Content-Type: application/json' -H 'Accept: application/json' \
#-d '{"items":[{"id":1, "description":"Standard Item 1"}]}' \
#http://localhost:7340/services/items
