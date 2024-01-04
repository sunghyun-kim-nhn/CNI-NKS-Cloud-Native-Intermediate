$("#btn1").click(function() {
	//var url = document.getElementById('url').value;
	var tenantId = document.getElementById('tid').value;
	var username = document.getElementById('auid').value;
	var password = document.getElementById('st_pwd').value;
	var obs = document.getElementById('obs').value;
	//var obs_list = "";

	var jsonData = {
		"auth": {
			"tenantId": tenantId,
			"passwordCredentials": {
				"username": username,
				"password": password
			}
		}
	};
	console.log("test 11: " + JSON.stringify(jsonData));
	$.ajax({
		url: "api/get_token.do",
		//data : {tenantId : tenantId, username : username, password : password},
		data: JSON.stringify(jsonData),
		type: "POST",
		//contentType: 'application/json; charset=utf-8',
		dataType: "json",
		beforeSend: function(xhr) { // 다중 헤더 추가 실시    				
			xhr.setRequestHeader("Access-Control-Allow-Origin", "*"); //헤더의 Content-Type을 설정		
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success: function(data) {
			var jsonData_result = {
				"token": data.access.token.id,
				"obs": obs
			};
			$.ajax({
				url: "api/get_container.do",
				//data : {tenantId : tenantId, username : username, password : password},
				data: JSON.stringify(jsonData_result),
				type: "POST",
				//contentType: 'application/json; charset=utf-8',
				dataType: "json",
				beforeSend: function(xhr) { // 다중 헤더 추가 실시    				
					xhr.setRequestHeader("Access-Control-Allow-Origin", "*"); //헤더의 Content-Type을 설정		
					xhr.setRequestHeader("Content-Type", "application/json");
				},
				success: function(data_list) {
					console.log(JSON.stringify(data_list));
					console.log("success : " + data);
					console.log("success1 : " + data_list);
					var html = "<br><br><label for='user_token'> 사용자 토큰 값:</label> <input type='text' class='form-control' id='token' name='token' ";
					html += "value='" + data.access.token.id + "' readonly>";
					html += "<br><br><label for='user_appkey'> SKM 사용 Appkey 값:</label> <input type='text' class='form-control' id='user_appkey' name='user_appkey' value='8bD7QWp4P2P0oNOH'>";
					html += "<br><label for='user_appkey'> SKM 사용 DB값 ID:</label> <input type='text' class='form-control' id='skm_id' name='skm_id' value='98f1158e174a44eaadf276fa1e6557bc'>";
					html += "<br><div class='dropdown'>";
					html += "<br><br><label for='user_container'> 검색된 OBS Container:</label>";
					html += "<br><div class='dropdown'>";
					html += "<button id='btn2' class='btn btn-primary dropdown-toggle' type='button' data-toggle='dropdown'>Container 선택</button>";
					html += "<div class='dropdown-menu' id='SelectMe'>"
					$.each(data_list, function(index, item) { // 데이터 =item
						console.log(index + " : " + item.c_name);
						//html += " <button type='button' class='btn btn-primary' onClick='redirect('" + item.c_name + "')'>"
						html += " <a class='dropdown-item' href='#'>"
							+ item.c_name
							+ "</a>";
					});
					html += "</div>";
					// <button type="button" id="btn1" class="btn btn-primary">OBS확인</button>
					$("#area1").html(html);
					$('.dropdown-menu a').click(function() {

						$("#btn2").text($(this).text());
						//console.log($(this).text());
						var c_name = $(this).text();
						var password = document.getElementById('st_pwd').value;
						var obs = document.getElementById('obs').value;
						var token = document.getElementById('token').value;
						var user_appkey = document.getElementById('user_appkey').value;
						var skm_id = document.getElementById('skm_id').value;
						//var c_name = container_name;
						//alert("obs 검색 : " + obs);
						console.log("Container 이름 체크 : " + c_name);
						console.log("token 체크 : " + token);
						console.log("password 체크 : " + password);
//						var request = new XMLHttpRequest();
//						request.onreadystatechange = function(oEvent) {
//							if (request.readyState == 4) {
//								result = 'Status: ' + request.status;
//								result = result + '\n' + request.getAllResponseHeaders();
//								console.log(result)
//							}
//						}
//						request.open('POST', "05_gallery_obs.html",true);
//						request.setRequestHeader('X-Auth-Token', token);
//						request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//						request.send("c_name=" + c_name + "&obs=" + obs);
//						request.open('GET', "05_gallery_obs.html?c_name=" + c_name + "&obs=" + obs);
//						request.setRequestHeader('X-Auth-Token', token);
//						request.send();
						window.location.replace("05_gallery_obs.html?c_name=" + c_name + "&obs=" + obs + "&token=" + token  + "&user_appkey=" + user_appkey  + "&skm_id=" + skm_id);
//						$.redirect('05_gallery_obs.html', {"c_name": c_name, "token": token});
					});
				},
				error: function() {
					alert("Container 조회 실패");
				}

			})
		},
		error: function() {
			console.log("ajax 처리 실패");
		}
	});
});

