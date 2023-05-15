function starryNight() {
  $('.star-rating .star').click(function() {
    var rating = $(this).data('rating');
    $('#rating').val(rating);

    // cambia il colore
    $('.star-rating .star').removeClass('yellow');
    $(this).addClass('yellow');
    $(this).prevAll().addClass('yellow');
    $(this).nextAll().removeClass('yellow');
  });
}

function updateReviewCounter() {
if($("#review_counter").length>0){
  var film = $("#filmId").val();
  $.ajax({
    url: "/review-count",
    method: "GET",
    data: { film: film },
    success: function(response) {
      console.log(response);
      $("#rating_value").text(response.average);
      $("#review_counter").text(response.ratings);
    },
    error: function(xhr, status, error) {
      console.log(error);
    }
  });
  }
}
function loadReviewModal(){
 $.ajax({
    url: "/load-reviews",
    method: "GET",
    success: function(response) {
      // console.log(response);

      if ($("#modal-overlay").length > 0) {
        console.log("Reviews are loaded already")
        loadReviews();

        $("#modal-overlay").fadeIn();
      } else {
        $("#reviewModal").html(response);
        $("#modal-overlay").fadeIn();
        $("#close-modal").click(function(){
            $("#modal-overlay").fadeOut();
        });
        $('#ratingFilter').on('change', applyFilter);
        loadReviews();


      }
      },
      error: function(xhr, status, error) {
          console.log(error);
        }
      });

}

function loadForm() {
  $.ajax({
    url: "/load-review-form",
    method: "GET",
    success: function(response) {
      // console.log(response);
      $("#write_a_review").hide();
      $("#write_a_review_statement").hide();

      if ($("#reviewForm").length > 0) {
        console.log("Review Form Already loaded");
      } else {
        $("#formContainer").html(response);
        $("#cancel_review_button").click(function() {
          $("#write_a_review").show();
          $("#write_a_review_statement").show();
          $("#reviewForm").remove();
        });

        starryNight();
        $('#submit_review').click(function(event) {
          var formData = {
            title: $('#title').val(),
            rating: parseInt($('#rating').val()),
            body: $('#body').val(),
            filmId: parseInt($("#filmId").val())
          };
          console.log(formData);

          // token CSRF
          var csrfToken = $("meta[name='_csrf']").attr("content");
          var csrfHeader = $("meta[name='_csrf_header']").attr("content");

          $.ajax({
            url: '/api/reviews/create',
            type: 'POST',
            data: formData,
            beforeSend: function(xhr) {
              xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
            $('#reviewForm').hide();
            if($("#errorMessage").hasClass('d-none')){
                $('#successMessage').removeClass('d-none');
                $('#close_success').click(function(){
                                    $("#successMessage").addClass('animate__fadeRightBig');
                                    $("#successMessage").addClass('d-none');
                                    $("#successMessage").removeClass('animate__fadeRightBig');
                                });
            }else{
                $("#errorMessage").addClass('d-none');
                $('#successMessage').removeClass('d-none');
                $('#successMessage').addClass('fadeIn');
                $('#close_success').click(function(){
                    $("#successMessage").addClass('animate__fadeRightBig');
                    $("#successMessage").addClass('d-none');
                    $("#successMessage").removeClass('animate__fadeRightBig');
                });
            }
              console.log('Review submitted successfully');
              $("#write_a_review").remove();
              $("#write_a_review_statement").remove();
              updateReviewCounter();
              console.log(response);
            },
            error: function(xhr, status, error) {
            $("#errorMessage").removeClass('d-none');
            $("#close_error").click(function(){
                $("#errorMessage").addClass('animate__fadeRightBig');
                 $("#errorMessage").addClass('d-none');
                $("#errorMessage").removeClass('animate__fadeRightBig');
            });
              console.log('Error submitting the review');
              console.log(error);
            }
          });
        });
      }
    },
    error: function(xhr, status, error) {
      console.log(error);
    }
  });
}

$(document).ready(function() {
    updateReviewCounter();

    $("#show_review_modal").click(function() {
        loadReviewModal();
      });
  if ($("#write_a_review").length > 0) {
    $("#write_a_review").click(function() {
      loadForm();
    });
  }
});