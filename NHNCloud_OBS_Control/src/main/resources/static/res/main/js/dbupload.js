$("#btn1").click(function() {
	//var url = document.getElementById('url').value;
	var url_href = window.location.href;
	var url = new URL(url_href);
	var c_title = document.getElementById('c_title').value;
	var y_code = document.getElementById('y_code').value;
	var c_name = url.searchParams.get("c_name");
	var token = url.searchParams.get("token");
	var obs = url.searchParams.get("obs");
	var user_appkey = url.searchParams.get('user_appkey');
	var skm_id = url.searchParams.get('skm_id');
	//var obs_list = "";
	console.log("Container 이름 체크 : " + c_name);
	console.log("token 체크 : " + token);
	console.log("c_title 체크 : " + c_title);

	$.ajax({
		url: "api/add_db_info.do",
		data: { 'c_title': c_title, 'y_code': y_code, 'token': token, 'c_name': c_name, 'obs': obs, 'user_appkey': user_appkey, 'skm_id': skm_id },
		type: "GET",
		//contentType: 'application/json; charset=utf-8',
		dataType: "text",
		beforeSend: function(xhr) { // 다중 헤더 추가 실시    				
			xhr.setRequestHeader("Access-Control-Allow-Origin", "*"); //헤더의 Content-Type을 설정		
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success: function() {
			//alert("obs 검색 : " + obs);
			console.log("Container 이름 체크 : " + c_name);
			console.log("token 체크 : " + token);
			console.log("c_title 체크 : " + c_title);

			window.location.replace("05_gallery_obs.html?c_name=" + c_name + "&obs=" + obs + "&token=" + token + "&user_appkey=" + user_appkey  + "&skm_id=" + skm_id);

		},
		error: function() {
			console.log("ajax 처리 실패");
		}
	});
});

