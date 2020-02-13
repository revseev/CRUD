let id;
let login;
let email;
let password;
let role;
let button;
let url = 'http://localhost:8080/api/all';
let allRoles = [];
let roles = [];
let userRoles = [];
let rolesFromServer = [];
let input;

fetch(url)
    .then(function (response) {
        return response.json()
    })
    .then(function (json) {
        for (let i in json) {
            id = json[i].id;
            login = json[i].login;
            email = json[i].email;
            password = json[i].password;
            role = json[i].roles;
            roles = [];
            for (let a in role) {
                roles.push(role[a].role);
            }
            button = '<button type="button"  class="btn btn-primary" id="' + id + '">Edit</button>';
            let row =
                "<tr>" +
                "<td>" + id + "</td>" +
                "<td>" + login + "</td>" +
                "<td>" + email + "</td>" +
                "<td>" + password + "</td>" +
                "<td>" + roles + "</td>" +
                "<td>" + button + "</td>" +
                "</tr>";
            $('#mytable').append(row);
        }

        $("td button").on('click', function() {
            $('#EditRole').html('');
            let i = $(this).attr('id');

            fetch(url)
                .then(function (response) {
                    return response.json()
                })
                .then(function (json) {
                    for (let a in json) {
                        if (json[a].id == i) {
                            id = json[a].id;
                            login = json[a].login;
                            email = json[a].email;
                            password = json[a].password;
                            currentRoles = json[a].roles;
                            userRoles = [];
                            for (let a in currentRoles) {
                                userRoles.push(currentRoles[a].role);
                            }
                            break;
                        }
                    }
                    fetch('http://localhost:8080/api/getroles')
                        .then(function (response) {
                            return response.json()
                        })
                        .then(function (json) {
                            allRoles = [];
                            rolesFromServer = json;
                            for (let i in json) {
                                let id = json[i].id;
                                let role = json[i].role;
                                allRoles.push(role);
                                input = '<label for="'+ role +'"><input type="checkbox" data="'+ id +'" class="" value="'+ role +'" name="role_edit" id="'+ role +'">'+ role +'</label>';
                                $('#EditRole').append(input);

                                for (let a = 0; a < allRoles.length; a ++) {
                                    if (userRoles[a] == allRoles[a]) {
                                        $('#' + userRoles[a]).attr("checked", "checked");
                                    } else {
                                        $('#' + userRoles[a]).attr("checked", "");
                                    }
                                }
                            }
                        });

                    $('form #Id').val(id);
                    $('#Login').val(login);
                    $('#Email').val(email);
                    $('#Password').val(password);
                    $('#exampleModal').modal('show');
                });
        });

        $('#edit_form').submit(function (e) {
            e.preventDefault();
            let id = $("#Id").val();
            let login = $("#Login").val();
            let email = $("#Email").val();
            let pass = $("#Password").val();
            let rolesToServer = [];
            $("input[name='role_edit']:checked").each(function() {
                let id = $(this).attr("data");
                let role = $(this).val();
                rolesToServer.push({"id": id, "role": role});
            });

            console.log(rolesToServer);
            fetch('http://localhost:8080/api/edit',{
                method: "POST",
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                },
                body:  JSON.stringify({
                    id: id,
                    login: login,
                    email: email,
                    password: pass,
                    roles: rolesToServer
                }),
            })
                .then(function(response){
                    return response.json();
                });
            location.reload(true);
        });

        $('#new_user').submit(function (e) {
            e.preventDefault();
            let login = $("#newLogin").val();
            let email = $("#newEmail").val();
            let pass = $("#newPassword").val();
            let rolesToServer = [];
            $("input[name='role']:checked").each(function() {
                let id = $(this).attr("data");
                let role = $(this).val();
                rolesToServer.push({"id": id, "role": role});
            });

            fetch('http://localhost:8080/api/add',{
                method: "POST",
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                },
                body:  JSON.stringify({
                    login: login,
                    email: email,
                    password: pass,
                    roles: rolesToServer
                }),
            })
                .then(function(response){
                    return response.json();
                });
            window.location.replace("/control/admin");
        });


        fetch('http://localhost:8080/api/getroles')
            .then(function (response) {
                return response.json()
            })
            .then(function (json) {
                allRoles = [];
                rolesFromServer = json;
                for (let i in json) {
                    let id = json[i].id;
                    let role = json[i].role;
                    allRoles.push(role);
                    input = '<label class="input_roles" for="'+ role +'"><input type="checkbox" data="'+ id +'" value="'+ role +'" name="role" id="'+ role +'">'+ role +'</label>';
                    $('#NewRole').append(input);
                }
            });
    });

