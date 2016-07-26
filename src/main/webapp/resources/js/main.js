/**
    This is main js class for consume rest service into ui
**/
$(document).ready(function() {

    jQuery.fn.exists = function() {
        return this.length > 0;
    }

    if($('#lrTabs').exists()) {
    	$('#lrTabs').tab()
    }

    var queryDict = {};
   location.search.substr(1).split("&").forEach(function(item) {queryDict[item.split("=")[0]] = item.split("=")[1]})
   console.log(queryDict['error']);
    if(queryDict.hasOwnProperty("error"))
    {
        alert("Your username and password is incorrect");
    }
     if(queryDict.hasOwnProperty("logout"))
     {
          alert("You are successfully logged out");
     }
    $.fn.serializeFormJSON = function () {

            var o = {};
            var a = this.serializeArray();
            $.each(a, function () {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        };

	$('#registerForm').on('submit', function(e){
            e.preventDefault();
            var data = $(this).serializeFormJSON();

            $.ajax({
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result,status,xhr)
                {
                   if(xhr.status == 200 )
                   {
                        alert(result);
                        location.reload();
                   }
                },
                error: function(xhr,status,error){
                    if(xhr.status == 400)
                    {
                        alert(error);
                    }
                },
                type: 'POST',
                url: 'register'
            });
            alert("You are successfully registered")
            $('#registerForm').trigger('reset');
             $('.nav-tabs a[href="#login"]').tab('show');
        });

                     $('#profileForm').on('submit',
                      function(e){
                        e.preventDefault();
                       var data = $(this).serializeFormJSON();

                                                   $.ajax({
                                                       contentType: 'application/json',
                                                       data: JSON.stringify(data),
                                                       dataType: 'json',
                                                       success: function(result,status,xhr)
                                                       {
                                                          if(xhr.status == 200 )
                                                          {
                                                               alert(result);
                                                               location.reload();
                                                          }
                                                       },
                                                       error: function(xhr,status,error){
                                                           if(xhr.status == 400)
                                                           {
                                                               alert(error);
                                                           }
                                                       },
                                                       type: 'PUT',
                                                       url: 'updateprofile'
                                                   });
                                                   alert("Your profile is successfully updated");
                                                   location.reload();
                                               });
});

 function loadDashBoard()
        {

             $.ajax({
                       dataType: 'json',
                       success: function(result,status,xhr)
                       {

                            if(xhr.status == 200 )
                            {
                                constructUser(result);
                                constructTable(result.books);
                                constructPublisher(result.publisherlist);
                            }

                            else if (xhr.status == 401)
                            {
                                 alert('Unauthorized access');
                                    location.href = 'logout';
                            }
                       },
                       error: function(xhr,status,error)
                       {
                             if(xhr.status == 500)
                             {
                                  alert(error);
                             }
                       },
                       type: 'GET',
                       url: 'getallbook'
                    });




        }

 function  constructTable(result)
 {

    $('#bookList').DataTable().destroy();
    $('#bookList > tbody').empty();
   for(var index in result)
   {
         var str = "<tr id='"+result[index].id+"'>"+
            "<td>"+result[index].title+"</td>"+
            "<td>"+result[index].publisherName+"</td>"+
            "<td>"+result[index].authors.join('\n')+"</td>"+
            "<td class='text-center' style='width: 30px !important;'><a href = '#' onclick = addData(this) ><i class='glyphicon glyphicon-plus'></i></a></td>"+
            "</tr>";

         $('#bookList tbody').append(str);
   }

   if($('#bookList').exists()) {
   	    $('#bookList').DataTable({
   			"columnDefs": [{
   				"orderable": false,
   				"targets": [-1],
   				"order": [[ 0, "asc" ]]
   			}]
   		});
   	}

 }

 function addData(param)
 {

    var table = $('#bookList').DataTable();
    $('#addDemandList').DataTable().destroy();
    var rowData = table.row($(param).parents('tr')).data();
    var str = "<tr id ='"+rowData.DT_RowId+"'>";
    for(var i = 0; i < rowData.length; i++)
    {
        if(i == 3)
        {
          break;
        }
        str += "<td>"+rowData[i]+"</td>";
    }
    str+= "<td class='text-center'><a href='#' onclick='removeData(this)'><i class='glyphicon glyphicon-remove'></i></a></td></tr>";

    $(str).appendTo($('#addDemandList tbody'));
    $('#addDemandList').DataTable({
        "columnDefs": [{
            "orderable": false,
            "targets": [-1],
            "order": [[ 0, "asc" ]]

        }]
    });

       	table.row( $(param).parents('tr') )
                        .remove()
                        .draw();

 }

 function removeData(param)
 {
   var table = $('#addDemandList').DataTable();
   $('#bookList').DataTable().destroy();
       var rowData = table.row($(param).parents('tr')).data();
       var str = "<tr id='"+rowData.DT_RowId+"'>";
       for(var i = 0; i < rowData.length; i++)
       {
           if(i == 3)
           {
             break;
           }
           str += "<td>"+rowData[i]+"</td>";
       }
        str+= "<td class='text-center'><a href='#' onclick='addData(this)'><i class='glyphicon glyphicon-plus'></i></a></td></tr>";
           $(str).appendTo($('#bookList tbody'));
              	    $('#bookList').DataTable({
              			"columnDefs": [{
              				"orderable": false,
              				"targets": [-1]
              			}]
              			,
              			"order": [[ 0, "asc" ]]
              		});

        table.row( $(param).parents('tr') )
                .remove()
                .draw();
 }

 function constructPublisher(publishers)
 {
    $.each(publishers, function (i, item) {
        $('#publisherlist').append($('<option>', {
            value: item,
            text : item
        }));
    });
    $("#publisherlist").select2();
 }

 function searchByPublisherName()
 {
    $.ajax({
                           dataType: 'json',
                           success: function(result,status,xhr)
                           {

                                if(xhr.status == 200 )
                                {

                                    constructUser(result);
                                    constructTable(result.books);
                                    constructPublisher(result.publisherlist);
                                }

                                else if (xhr.status == 401)
                                {
                                     alert('Unauthorized access');
                                        location.href = 'logout';
                                }
                           },
                           error: function(xhr,status,error)
                           {
                                 if(xhr.status == 500)
                                 {
                                      alert(error);
                                 }
                           },
                           type: 'POST',
                           data: {"publisherName": $('#publisherlist').val()},
                           url: 'searchbypublisher'
                        });

 }

 function saveIntoDemandList()
 {
    var table = $('#addDemandList').DataTable()
    var data = table.rows().data();
    var demandList = [];
    for(var i = 0; i < data.length; i++)
       {
             demandList.push({"id":data[i].DT_RowId});
       }
     if(demandList.length > 0)
     {
        var userDemands = {"books":demandList}
        $.ajax({
                        contentType: 'application/json',
                        data: JSON.stringify(userDemands),
                        dataType: 'json',
                        success: function(result,status,xhr)
                        {
                           console.log(xhr);
                           if(xhr.status == 200 )
                           {
                                alert(result);
                                location.reload();
                           }
                        },
                        error: function(xhr,status,error){
                            if(xhr.status == 400 || xhr.status == 500)
                            {
                                alert(error);
                            }
                        },
                        type: 'POST',
                        url: 'addbookdemands'
                    });

         location.reload();
    }
 }

 function previousDemand()
 {
    $.ajax({
                           dataType: 'json',
                           success: function(result,status,xhr)
                           {

                                if(xhr.status == 200 )
                                {
                                    constructUser(result);
                                    constructPreviousDemands(result.previousdemands);
                                }

                                else if (xhr.status == 401)
                                {
                                     alert('Unauthorized access');
                                        location.href = 'logout';
                                }
                           },
                           error: function(xhr,status,error)
                           {
                                 if(xhr.status == 500)
                                 {
                                      alert("Internal Server error");
                                 }
                           },
                           type: 'GET',
                           url: 'previousdemands'
                        });
 }

 function constructPreviousDemands(data)
 {
    $('#demandList').DataTable().destroy();
    $('#demandList > tbody').empty()
    if(data)
    {
        for(var i in data)
        {
            for(var index in data[i].books)
                    {
                         var str = "<tr id='"+data[i].id+"'>"+
                                    "<td>"+data[i].books[index].title+"</td>"+
                                    "<td>"+data[i].books[index].publisherName+"</td>"+
                                    "<td>"+data[i].books[index].authors.join('\n')+"</td>"+
                                    "<td>"+new Date(data[i].placedDate).toString()+"</td>"+
                                    "<td class='text-center' style='width: 30px !important;'><a href = '#' onclick = 'removeDemands(this)' id= '"+data[i].books[index].id+"' ><i class='glyphicon glyphicon-remove'></i></a></td>"+
                                    "</tr>";
                                 $('#demandList tbody').append(str);
                    }
        }
               	    $('#demandList').DataTable({
               			"columnDefs": [{
               				"orderable": false,
               				"targets": [-1]
               			}],
               			"order": [[ 4, "asc" ]]
               		});
    }
 }

 function removeDemands(param)
 {
    $.ajax({
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function(result,status,xhr)
                    {
                       if(xhr.status == 200)
                       {
                          constructUser(result);
                          constructPreviousDemands(result.previousdemands);
                       }
                    },
                    error: function(xhr,status,error){
                        if(xhr.status == 500)
                        {
                            alert("Internal server error")
                        }
                    },
                    type: 'PUT',
                    url: 'updatepreviousdemands/'+$(param).parents('tr').attr('id')+'/'+$(param).attr('id')
                });

 }
 function constructUser(result)
 {
    $('#username').text(result.user.userName);
    $('#userId').val(result.id);
    $('#firstName').val(result.user.firstName);
    $('#lastName').val(result.user.lastName);
    $('#password').val(result.user.password);
    $('#universityName').val(result.user.universityName);
 }

 function existUser(param)
 {
    $.ajax({
                        contentType: 'application/json',
                        dataType: 'json',
                        success: function(result,status,xhr)
                        {
                           console.log(result);
                           if(!result)
                          {
                            alert("You are already registered");
                            $(param).val('');
                            $(param).focus();
                          }
                        },
                        error: function(xhr,status,error){
                            if(xhr.status == 500)
                            {
                                alert("Internal server error")
                            }
                        },
                        type: 'POST',
                        url: 'existuser/'+$(param).val()
                    });
 }