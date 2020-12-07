/* This will let you use the .remove() function later on */
if (!('remove' in Element.prototype)) {
	Element.prototype.remove = function() {
		if (this.parentNode) {
			this.parentNode.removeChild(this);
		}
	};
}

mapboxgl.accessToken = 'pk.eyJ1Ijoib2thbmF3YXRpIiwiYSI6ImNrZzhsOXlueDBpZmYyeW8yZnFoaHplOGMifQ.oVTSOlNLzBcJN7EfekHy9g';

var map = new mapboxgl.Map({
	container: document.getElementById('map').id,
	style: 'mapbox://styles/mapbox/streets-v11',
	center: [-79.347015, 43.651070],
	zoom: 8
});

var geolocate = new mapboxgl.GeolocateControl({
	positionOptions: {
		enableHighAccuracy: true
	},
	trackUserLocation: true,
	showAccuracyCircle: false
});

map.addControl(geolocate);

//retrieves geocoder API string
var geocoder = document.getElementById("geocoder").value;

//initialize shop list
var shops = [];

//removes square brackets around list of shop names
shopNames = shopNames.substring(1, shopNames.length-1);
shopNames = shopNames.split(', ');

shopIDs = shopIDs.substring(1, shopIDs.length-1);
shopIDs = shopIDs.split(', ');

//assigns json object to shops variable
fetch(geocoder)
.then((response) => {
	return response.json();
})
.then((data) => {
	shops = data;

	// 
	if (Array.isArray(shops)) {
		for (var i = 0; i < shops.length; i++) {
			shops[i].features[0].id = i;
		}
	} else {
		shops.features[0].id = 0;
	}
	console.log(shops);
})
.catch((err) => {

});

//on map load, add a new layer for each store
map.on('load', function(e) {
	geolocate.trigger();
	map.addSource('places', {
		type: 'geojson',
		data: shops
	});
	buildLocationList(shops);
	addMarkers();
});

function addMarkers() {

	if (Array.isArray(shops)) {
		for (var i = 0; i < shops.length; i++) {
			shops[i]["features"].forEach(function(marker) {
				/* Create a div element for the marker. */
				var el = document.createElement('div');
				/* Assign a unique `id` to the marker. */
				el.id = "marker-" + marker.id;
				/* Assign the `marker` class to each marker for styling. */
				el.className = 'marker';

				/**
				 * Create a marker using the div element
				 * defined above and add it to the map.
				 **/
				new mapboxgl.Marker(el, { offset: [0, -23] })
				.setLngLat(marker.geometry.coordinates)
				.addTo(map);
				
				el.addEventListener('click', function (e) {
					
					for (var j = 0; j < shops.length; j++) {
						if (this.id == "marker-" + j) {
							/* Fly to the point */
							flyToShop(marker);
							/* Close all other popups and display popup for clicked store */
							createPopUp(marker, shopNames[j], shopIDs[j]);
						}
					}
		            /* Highlight listing in sidebar */
		            var activeItem = document.getElementsByClassName('active');
		            e.stopPropagation();
		            if (activeItem[0]) {
		              activeItem[0].classList.remove('active');
		            }
		            var listing = document.getElementById(
		              'listing-' + marker.id
		            );
		            listing.classList.add('active');
		          });
			});
		}
	}
	else {
		shops.features.forEach(function(marker) {
			/* Create a div element for the marker. */
			var el = document.createElement('div');
			/* Assign a unique `id` to the marker. */
			el.id = "marker-" + marker.id;
			/* Assign the `marker` class to each marker for styling. */
			el.className = 'marker';

			/**
			 * Create a marker using the div element
			 * defined above and add it to the map.
			 **/
			new mapboxgl.Marker(el, { offset: [0, -23] })
			.setLngLat(marker.geometry.coordinates)
			.addTo(map);
			
			el.addEventListener('click', function (e) {
	            /* Fly to the point */
	            flyToShop(marker);
	            /* Close all other popups and display popup for clicked store */
	            createPopUp(marker, shopNames[0], shopIDs[0]);
	            /* Highlight listing in sidebar */
	            var activeItem = document.getElementsByClassName('active');
	            e.stopPropagation();
	            if (activeItem[0]) {
	              activeItem[0].classList.remove('active');
	            }
	            var listing = document.getElementById(
	              'listing-' + marker.id
	            );
	            listing.classList.add('active');
	          });
		});
	}
}

function buildLocationList(data) {
	if (Array.isArray(shops)) {
		for (var i = 0; i < shops.length; i++) {
			/**
			 * Create a shortcut for `store.properties`,
			 * which will be used several times below.
			 **/
			var prop = shops[i]["features"][0];

			/* Add a new listing section to the sidebar. */
			var listings = document.getElementById('listings');
			var listing = listings.appendChild(document.createElement('div'));
			/* Assign a unique `id` to the listing. */
			listing.id = "listing-" + prop.id;
			/* Assign the `item` class to each listing for styling. */
			listing.className = 'item';

			/* Add the link to the individual listing created above. */
			var link = listing.appendChild(document.createElement('a'));
			link.href = '#';
			link.className = 'title';
			link.id = "link-" + prop.id;
			link.innerHTML = shopNames[i];

			/* Add details to the individual listing. */
			var details = listing.appendChild(document.createElement('div'));
			details.innerHTML = prop.address + " " + prop.text;
			/*if (prop.phone) {
              details.innerHTML += ' · ' + prop.phoneFormatted;
            }*/

			link.addEventListener('click', function(e) {
				for (var j = 0; j < data.length; j++) {
					if (this.id === "link-" + data[j].features[0].id) {
						var clickedListing = data[j].features[0];
						flyToShop(clickedListing);
						createPopUp(clickedListing, shopNames[j], shopIDs[j]);
					}
				}
				var activeItem = document.getElementsByClassName('active');
				if (activeItem[0]) {
					activeItem[0].classList.remove('active');
				}
				this.parentNode.classList.add('active');
			});
		};
	} else {
		var prop = shops.features[0];

		var listings = document.getElementById('listings');
		var listing = listings.appendChild(document.createElement('div'));

		listing.id = "listing-" + prop.id;

		listing.className = 'item';

		var link = listing.appendChild(document.createElement('a'));
		link.href = '#';
		link.className = 'title';
		link.id = "link-" + prop.id;
		link.innerHTML = shopNames[0];

		var details = listing.appendChild(document.createElement('div'));
		details.innerHTML = prop.address + " " + prop.text;

		link.addEventListener('click', function(e) {

			var clickedListing = prop;
			flyToShop(clickedListing);
			createPopUp(clickedListing, shopNames[0], shopIDs[0]);

			var activeItem = document.getElementsByClassName('active');
			if (activeItem[0]) {
				activeItem[0].classList.remove('active');
			}
			this.parentNode.classList.add('active');
		});
	}
}

function flyToShop(currentFeature) {
	map.flyTo({
		center: currentFeature.geometry.coordinates,
		zoom: 15
	})
}

function createPopUp(currentFeature, shopName, shopID) {
	var popUps = document.getElementsByClassName('mapboxgl-popup');
	/** Check if there is already a popup on the map and if so, remove it */
	if (popUps[0]) popUps[0].remove();

	var popup = new mapboxgl.Popup({
		closeOnClick: false
	})
	.setLngLat(currentFeature.geometry.coordinates)
	.setHTML('<h3>' + shopName + '</h3>' +
			'<h4>' + currentFeature.address + ' ' + currentFeature.text + '</h4>' +
			'<form id="shopID" action="/createAppointment">' +
			'<input type=hidden value=' + parseInt(shopID) + ' name="shopID">' +
			'<input type=submit value="Book Appointment">' +
	'</form>')
	.addTo(map);
}