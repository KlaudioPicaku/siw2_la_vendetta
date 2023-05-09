  $(document).ready(function() {
    if ($('#selected-images').length > 0) {
      $('#image').on('change', function() {
        var selectedImagesContainer = $('#selected-images');
        var files = $(this).prop('files');

        for (var i = 0; i < files.length; i++) {
          var file = files[i];
          var imageContainer = $('<div>').addClass('selected-image');

          var image = $('<img>').attr('src', URL.createObjectURL(file)).addClass('selected-image-thumbnail mt-2');
          image.attr('width', '50');
          image.attr('height', '50');
          imageContainer.append(image);

          var deselectButton = $('<button>').addClass('btn btn-danger').text('X');
          deselectButton.on('click', function() {
            $(this).parent().remove();
             $("#selected-images").val('');
          });
          imageContainer.append(deselectButton);

          selectedImagesContainer.append(imageContainer);
        }

//        $(this).val('');
      });
    }
  });