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
    center: [-79.644234, 43.590238],
    zoom: 14
});


// retrieves geocoder API string
var geocoder = document.getElementById("geocoder").value;

// initialize shop list (hard coded for testing)

var shops = [];

/*var shops = [{
    "type": "FeatureCollection",
    "query": ["5602", "tenth", "line", "w", "122", "mississauga", "on"],
    "features": [{
        "id": "address.4800875659823766",
        "type": "Feature",
        "place_type": ["address"],
        "relevance": 0.797619,
        "properties": {
            "accuracy": "point"
        },
        "text": "Tenth Line West",
        "place_name": "5602 Tenth Line West, Mississauga, Ontario L5M 7E2, Canada",
        "center": [-79.743798, 43.558375],
        "geometry": {
            "type": "Point",
            "coordinates": [-79.743798, 43.558375]
        },
        "address": "5602",
        "context": [{
            "id": "neighborhood.16435117286408010",
            "text": "Churchill Meadows"
        }, {
            "id": "postcode.14843315390233500",
            "text": "L5M 7E2"
        }, {
            "id": "place.12145266183380350",
            "wikidata": "Q50816",
            "text": "Mississauga"
        }, {
            "id": "region.10598799396263190",
            "wikidata": "Q1904",
            "short_code": "CA-ON",
            "text": "Ontario"
        }, {
            "id": "country.10278600750587150",
            "wikidata": "Q16",
            "short_code": "ca",
            "text": "Canada"
        }]
    }],
    "attribution": "© 2020 Mapbox and its suppliers. All rights reserved. Use of this data is subject to the Mapbox Terms of Service. (https://www.mapbox.com/about/maps/)"
}, {
    "type": "FeatureCollection",
    "query": ["675", "the", "chase", "21", "mississauga", "on"],
    "features": [{
        "id": "address.3133399086200702",
        "type": "Feature",
        "place_type": ["address"],
        "relevance": 0.712963,
        "properties": {
            "accuracy": "street"
        },
        "text": "the Chase",
        "place_name": "the Chase, Mississauga, Ontario L5M 2Y8, Canada",
        "center": [-79.6966585, 43.5636707],
        "geometry": {
            "type": "Point",
            "coordinates": [-79.6966585, 43.5636707]
        },
        "context": [{
            "id": "postcode.19298927985173810",
            "text": "L5M 2Y8"
        }, {
            "id": "place.12145266183380350",
            "wikidata": "Q50816",
            "text": "Mississauga"
        }, {
            "id": "region.10598799396263190",
            "wikidata": "Q1904",
            "short_code": "CA-ON",
            "text": "Ontario"
        }, {
            "id": "country.10278600750587150",
            "wikidata": "Q16",
            "short_code": "ca",
            "text": "Canada"
        }]
    }],
    "attribution": "© 2020 Mapbox and its suppliers. All rights reserved. Use of this data is subject to the Mapbox Terms of Service. (https://www.mapbox.com/about/maps/)"
}, {
    "type": "FeatureCollection",
    "query": ["243", "queen", "st", "s", "mississauga", "on"],
    "features": [{
        "id": "address.8494029921921510",
        "type": "Feature",
        "place_type": ["address"],
        "relevance": 1,
        "properties": {
            "accuracy": "point"
        },
        "text": "Queen Street South",
        "place_name": "243 Queen Street South, Mississauga, Ontario L5M 1Z5, Canada",
        "center": [-79.711742, 43.580448],
        "geometry": {
            "type": "Point",
            "coordinates": [-79.711742, 43.580448]
        },
        "address": "243",
        "context": [{
            "id": "neighborhood.7649906197038000",
            "text": "East Credit"
        }, {
            "id": "postcode.14225916045492640",
            "text": "L5M 1Z5"
        }, {
            "id": "place.12145266183380350",
            "wikidata": "Q50816",
            "text": "Mississauga"
        }, {
            "id": "region.10598799396263190",
            "wikidata": "Q1904",
            "short_code": "CA-ON",
            "text": "Ontario"
        }, {
            "id": "country.10278600750587150",
            "wikidata": "Q16",
            "short_code": "ca",
            "text": "Canada"
        }]
    }],
    "attribution": "© 2020 Mapbox and its suppliers. All rights reserved. Use of this data is subject to the Mapbox Terms of Service. (https://www.mapbox.com/about/maps/)"
}]*/

// assigns json object to shops variable
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

/*for (var i = 0; i < shops.length; i++) {
    shops[i]["features"][0].id = i;
}*/


// on map load, add a new layer for each store
map.on('load', function(e) {
    if (Array.isArray(shops)) {
        shops.forEach(function(shop, i) {
            /* Add the data to your map as a layer */
            map.addLayer({
                "id": "locations" + i,
                "type": "symbol",
                /* Add a GeoJSON source containing place coordinates and information. */
                "source": {
                    "type": "geojson",
                    "data": shop
                },
                "layout": {
                    "icon-image": "restaurant-15",
                    "icon-allow-overlap": true,
                }
            });
        });
    } else {
        map.addLayer({
            "id": "locations",
            "type": "symbol",
            /* Add a GeoJSON source containing place coordinates and information. */
            "source": {
                "type": "geojson",
                "data": shops
            },
            "layout": {
                "icon-image": "restaurant-15",
                "icon-allow-overlap": true,
            }
        })
    }
    buildLocationList(shops);
});

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
            link.innerHTML = prop.address + " " + prop.text;

            /* Add details to the individual listing. */
            var details = listing.appendChild(document.createElement('div'));
            details.innerHTML = prop.context[2].text;
            /*if (prop.phone) {
              details.innerHTML += ' · ' + prop.phoneFormatted;
            }*/

            link.addEventListener('click', function(e) {
                for (var j = 0; j < data.length; j++) {
                    if (this.id === "link-" + data[j].features[0].id) {
                        var clickedListing = data[j].features[0];
                        flyToShop(clickedListing);
                        createPopUp(clickedListing);
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
        link.innerHTML = prop.address + " " + prop.text;

        link.addEventListener('click', function(e) {

            var clickedListing = prop;
            flyToShop(clickedListing);
            createPopUp(clickedListing);

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

function createPopUp(currentFeature) {
    var popUps = document.getElementsByClassName('mapboxgl-popup');
    /** Check if there is already a popup on the map and if so, remove it */
    if (popUps[0]) popUps[0].remove();

    var popup = new mapboxgl.Popup({
            closeOnClick: false
        })
        .setLngLat(currentFeature.geometry.coordinates)
        .setHTML('<h3>' + document.getElementById('shopName').innerHTML + '</h3>' +
            '<h4>' + currentFeature.address + ' ' + currentFeature.text + '</h4>')
        .addTo(map);
}



map.on('click', function(e) {
    /* Determine if a feature in the "locations" layer exists at that point. */
    var features = map.queryRenderedFeatures(e.point, {
        layers: ['locations']
    });

    /* If yes, then: */
    if (features.length) {
        var clickedPoint = features[0];

        /* Fly to the point */
        flyToStore(clickedPoint);

        /* Close all other popups and display popup for clicked store */
        createPopUp(clickedPoint);

        /* Highlight listing in sidebar (and remove highlight for all other listings) */
        var activeItem = document.getElementsByClassName('active');
        if (activeItem[0]) {
            activeItem[0].classList.remove('active');
        }
        var listing = document.getElementById('listing-' + clickedPoint.properties.id);
        listing.classList.add('active');
    }
});