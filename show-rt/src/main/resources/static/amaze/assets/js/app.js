(function($) {
  'use strict';

  $(function() {
    var $fullText = $('.admin-fullText');
    $('#admin-fullscreen').on('click', function() {
      $.AMUI.fullscreen.toggle();
    });

    $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
      $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
    });
  });
})(jQuery);

function getContextPath() {
  var pathName = document.location.pathname;
  var index = pathName.substr(1).indexOf("/");
  var result = pathName.substr(0,index+1);
  return result;
}

// http://stackoverflow.com/questions/8648892/convert-url-parameters-to-a-javascript-object
function parseSearch() {
  var search = window.location.search.substring(1);
  return JSON.parse('{"' + decodeURI(search).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g,'":"') + '"}');
}
