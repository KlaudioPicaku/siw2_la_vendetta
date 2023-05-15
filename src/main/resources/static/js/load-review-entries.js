var page = 1; // Track the current page number
var isLoading = false; // Track if a request is already in progress
var filter = ""; // Track the current rating filter

function loadReviews() {
  if (isLoading) return; // Prevent multiple simultaneous requests
  isLoading = true;
  var film = $("#filmId").val();
if($("#reviewsContainer").length>0){
  // Send a request to fetch reviews from your server or API
  $.ajax({
    url: '/api/reviews/load',
    method: 'GET',
    data: { page: page, film: film, rating: filter },
    success: function(response) {
      // Handle the response and append the reviews to the container
      var reviews = response.reviews;
      var $container = $('#reviewsContainer');
//      console.log(response);

      for (var i = 0; i < response.length; i++) {
        // Create HTML elements for each review and append to container
        var $review = $(`
          <div class="review-entry-container rounded p-3 bg-light">
            <div class="row">
              <div class="col-2">
                <img src="${response[i].reviewPic}" class="rounded-circle" width="50" height="50" alt="Profile Image" />
              </div>
              <div class="col-8">
                <h4>${response[i].reviewAuthor}</h4>
                <h2>${response[i].reviewTitle}</h2>
                <p>${response[i].reviewBody}</p>
              </div>
              <div class="col-2">
                <div class="text-right">
                  <span class="star">&#9733;</span>
                  <span class="rating-value text-black">${response[i].reviewRating}/5</span>
                </div>
              </div>
            </div>
          </div>
        `);
        $container.append($review);
      }

//      console.log(page);
      isLoading=false;
      // Attach the scroll event listener to the reviews container

    },
    error: function() {
      isLoading = false; // Reset the loading flag in case of an error
    }
  });
     $('#load_more_reviews').click(function(){
        if($('.review-entry-container').length<(parseInt($('#review_counter').text()))){
           page++;
//           console.log(page);
           loadReviews();
        }
     });
}
}



function applyFilter() {
  // Clear the current reviews and reset the page number
  $('#reviewsContainer').empty();
  page = 1;

  // Get the selected rating filter value
  var selectedRating = $('#ratingFilter').val();

  // Set the filter value
  filter = selectedRating;

  // Load reviews based on the new filter
  loadReviews();
}

$(document).ready(function() {



});