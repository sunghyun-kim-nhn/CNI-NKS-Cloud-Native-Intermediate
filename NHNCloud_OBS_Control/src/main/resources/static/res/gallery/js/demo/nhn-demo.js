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

	// Load demo images from Flickr:
	$.ajax({
		url: 'https://kr1-api-object-storage.nhncloudservice.com/v1/AUTH_4a012981ac48456c94b54f992277d465/Source_container/',
		//url: 'https://api.flickr.com/services/rest/',    
		dataType: 'json',
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

			if (photo.content_type == "image/jpeg") {				
			

			console.log("lv2 Start : " + count);
			//const obj2 = JSON.stringify(key);
			console.log("obj2 : " + photo.name);
			var thumbnail = $('<img>')
				.prop('loading', 'lazy')
				.prop('width', '75')
				.prop('height', '75')
				.prop('src', 'https://kr1-api-object-storage.nhncloudservice.com/v1/AUTH_4a012981ac48456c94b54f992277d465/Source_container/' + photo.name)
				.prop('alt', photo.last_modified)
			var srcset = []
			srcset = srcset.join(',')
			$('<a></a>')
				.append(thumbnail)
				.prop('title', photo.last_modified)
				.prop('href', 'https://kr1-api-object-storage.nhncloudservice.com/v1/AUTH_4a012981ac48456c94b54f992277d465/Source_container/' + photo.name)
				.attr('data-srcset', srcset)
				.attr('data-gallery', '')
				.appendTo(linksContainer)
			carouselLinks.push({
				title: photo.last_modified,
				href: 'https://kr1-api-object-storage.nhncloudservice.com/v1/AUTH_4a012981ac48456c94b54f992277d465/Source_container/' + photo.name,
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

	// Initialize the Gallery as video carousel:
	// eslint-disable-next-line new-cap
	blueimp.Gallery(
		[
			{
				title: 'Sintel',
				type: 'video',
				sources: [
					{
						type: 'video/webm',
						src:
							'https://upload.wikimedia.org/wikipedia/commons/f/f1/' +
							'Sintel_movie_4K.webm'
					},
					{
						type: 'video/mp4',
						src: 'https://archive.org/download/Sintel/sintel-2048-surround.mp4'
					},
					{
						type: 'video/ogg',
						src: 'https://archive.org/download/Sintel/sintel-2048-stereo.ogv'
					}
				],
				poster:
					'https://upload.wikimedia.org/wikipedia/commons/d/dc/' +
					'Sintel_1920x1080.png'
			},
			{
				title: 'LES TWINS - An Industry Ahead',
				type: 'text/html',
				youtube: 'zi4CIXpx7Bg'
			},
			{
				title: 'KN1GHT - Last Moon',
				type: 'text/html',
				vimeo: '73686146',
				poster: 'https://secure-a.vimeocdn.com/ts/448/835/448835699_960.jpg'
			}
		],
		{
			container: '#blueimp-video-carousel',
			carousel: true,
			startSlideshow: false
		}
	)

	$('#fullscreen').change(function() {
		$('#blueimp-gallery').data('fullscreen', this.checked)
	})
})
