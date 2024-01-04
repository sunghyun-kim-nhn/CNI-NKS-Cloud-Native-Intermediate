function obsupload() {
	//var url = document.getElementById('url').value;
	var url_href = window.location.href;
	var url = new URL(url_href);
	console.log(url_href);
	console.log(url.searchParams.get("c_name"));
	console.log(url.searchParams.get("token"));
	const c_name = url.searchParams.get("c_name");
	const token = url.searchParams.get("token");
	const obs = url.searchParams.get("obs");
	const user_appkey = url.searchParams.get("user_appkey");
	const skm_id = url.searchParams.get("skm_id");
	//	const final_url = obs + "/" + c_name + "/";
	//	var result = "";

	window.location.replace("06_gallery_file_upload_obs.html?c_name=" + c_name + "&obs=" + obs + "&token=" + token + "&user_appkey=" + user_appkey + "&skm_id=" + skm_id);
}

function dbupload() {
	//var url = document.getElementById('url').value;
	var url_href = window.location.href;
	var url = new URL(url_href);
	console.log(url_href);
	console.log(url.searchParams.get("c_name"));
	console.log(url.searchParams.get("token"));
	const c_name = url.searchParams.get("c_name");
	const token = url.searchParams.get("token");
	const obs = url.searchParams.get("obs");
	const user_appkey = url.searchParams.get("user_appkey");
	const skm_id = url.searchParams.get("skm_id");
	//	const final_url = obs + "/" + c_name + "/";
	//	var result = "";
	//


	window.location.replace("07_gallery_upload_db.html?c_name=" + c_name + "&obs=" + obs + "&token=" + token + "&user_appkey=" + user_appkey + "&skm_id=" + skm_id);
}

