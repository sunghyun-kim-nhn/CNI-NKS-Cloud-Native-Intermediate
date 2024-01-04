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

function getContextPath() {
	var hostIndex = location.href.indexOf(location.host) + location.host.length;
	return location.href.substring(hostIndex, location.href.indexOf('/', hostIndex + 1));
}

$(function() {
	'use strict'
	//console.log(location.host);
	//console.log(getContextPath());
	var url = location.host + getContextPath();
	console.log(url);

	// Load demo images from Flickr:
	$.ajax({
		url: 'http://' + url + '/api/get_local_file.do',
		//url: 'https://api.flickr.com/services/rest/',    
		dataType: 'json',
		type: 'post'
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
					.prop('src', 'http://' + url + '/upload/' + photo.name)
					.prop('alt', photo.last_modified)
				var srcset = []
				srcset = srcset.join(',')
				$('<a></a>')
					.append(thumbnail)
					.prop('title', photo.last_modified)
					.prop('href', 'http://' + url + '/upload/' + photo.name)
					.attr('data-srcset', srcset)
					.attr('data-gallery', '')
					.appendTo(linksContainer)
				carouselLinks.push({
					title: photo.last_modified,
					href: 'http://' + url + '/upload/' + photo.name,
					sizes: sizes,
					srcset: srcset
				});
			}
		});
		// Initialize the Gallery as image carousel:
		// eslint-disable-next-line new-cap
		blueimp.Gallery(carouselLinks, {
			container: '#blueimp-image-carousel',
			carousel: true
		})
	})

	// Initialize the Gallery as video carousel:
	// eslint-disable-next-line new-cap
	$.ajax({
		url: 'http://' + url + '/api/get_json_info.do',
		dataType: 'json',
		type: 'post'
	}).done(function(result_local_json) {
		console.log(result_local_json);
		const obj0 = JSON.stringify(result_local_json);
		console.log("=======Movie=======");
		console.log("obj0 : " + obj0);
		console.log("=======================");
		//var obj2 = obj0;		
		// Add the demo images as links with thumbnails to the page:		
		blueimp.Gallery(
			result_local_json,
			{
				container: '#blueimp-video-carousel',
				carousel: true,
				startSlideshow: false
			}
		)
	});

	$('#fullscreen').change(function() {
		$('#blueimp-gallery').data('fullscreen', this.checked)
	});
})
