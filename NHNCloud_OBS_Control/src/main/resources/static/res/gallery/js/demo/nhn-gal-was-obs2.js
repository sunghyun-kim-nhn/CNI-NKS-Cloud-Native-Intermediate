/*
 * blueimp Gallery Demo JS
 * https://github.com/blueimp/Gallery
 *
 * Copyright 2013, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * https://opensource.org/licenses/MIT
 */

/* global blueimp, $ */

$(function() {
	'use strict'
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
	const final_url = obs + "/" + c_name + "/";
	var result = "";

	var request = new XMLHttpRequest();
	request.onreadystatechange = function(oEvent) {
		if (request.readyState == 4) {
			result = 'Status: ' + request.status;
			result = result + '\n' + request.getAllResponseHeaders();
			console.log(result)
		}
	}
	request.open('GET', final_url);
	request.setRequestHeader('X-Auth-Token', token);
	request.send(null);


	// Load demo images from Flickr:
	$.ajax({
		//url: final_url,
		url: 'api/get_obs_file.do', 
		type: 'get',   
		dataType: 'json',
		data: {'c_name': c_name, 'obs': obs},
	}).done(function(result) {
		console.log(result);
		const obj0 = JSON.stringify(result);
		console.log("=======================");
		console.log("obj0 : " + obj0);
		console.log("=======================");
		var maxWidth = $(document.body).css('max-width')
		var sizes = '(min-width: ' + maxWidth + ') ' + maxWidth + ', 100vw'
		var carouselLinks = []
		var linksContainer = $('#links')
		//var obj2 = obj0;		
		// Add the demo images as links with thumbnails to the page:
		var count = 0;
		$.each(result, function(key, photo) {
			//const obj1 = JSON.stringify(result.photos);

			if (photo.content_type == "image/jpeg" || photo.content_type == "image/gif" || photo.content_type == "image/png" || photo.content_type == "image/svg+xml") {


				console.log("lv2 Start : " + count);
				//const obj2 = JSON.stringify(key);
				console.log("obj2 : " + photo.name);
				var thumbnail = $('<img>')
					.prop('loading', 'lazy')
					.prop('width', '75')
					.prop('height', '75')
					.prop('src', final_url + photo.name)
					.prop('alt', photo.last_modified)
				var srcset = []
				srcset = srcset.join(',')
				$('<a></a>')
					.append(thumbnail)
					.prop('title', photo.last_modified)
					.prop('href', final_url + photo.name)
					.attr('data-srcset', srcset)
					.attr('data-gallery', '')
					.appendTo(linksContainer)
				carouselLinks.push({
					title: photo.last_modified,
					href: final_url + photo.name,
					sizes: sizes,
					srcset: srcset
				})
			}
		})
		// Initialize the Gallery as image carousel:
		// eslint-disable-next-line new-cap
		blueimp.Gallery(carouselLinks, {
			container: '#blueimp-image-carousel',
			carousel: true
		})
	})
	
	$.ajax({
		url: 'api/get_db_info.do',    
		dataType: 'json',
		type: 'get',
		data: {'c_name': c_name, 'user_appkey': user_appkey, 'skm_id': skm_id}
	}).done(function(result) {
		console.log(result);
		const obj0 = JSON.stringify(result);
		console.log("=======Movie=======");
		console.log("obj0 : " + obj0);
		console.log("=======================");		
		//var obj2 = obj0;		
		// Add the demo images as links with thumbnails to the page:		
		blueimp.Gallery(
		result,
		{
			container: '#blueimp-video-carousel',
			carousel: true,
			startSlideshow: false
		}
	)
	})
	

	$('#fullscreen').change(function() {
		$('#blueimp-gallery').data('fullscreen', this.checked)
	})
})
