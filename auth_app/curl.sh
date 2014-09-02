# expect: NO_USERNAME
#curl -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{ "userName": "", "password": "p1" } }' http://localhost:8003/services/security/authToken

# expect: NO_PASSWORD
#curl -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{ "userName": "u1"} }' http://localhost:8003/services/security/authToken

# expect: USER_NOT_FOUND
#curl -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{ "userName": "u1", "password": "p1" } }' http://localhost:8003/services/security/authToken

# expect: WRONG_PASSWORD
#curl -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{ "userName": "wmt_user1", "password": "p1" } }' http://localhost:8003/services/security/authToken

# expect: OK 
curl -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{ "userName": "wmt_user1", "password": "wmt_password1" } }' http://localhost:8003/services/security/authToken

#curl -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{ "userName": "wmt_user1", "password": "wmt_password1" } }' http://localhost:8003/services/other
echo "\n"
