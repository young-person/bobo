var indexModel = (function () {
    return {
        initFrameHeight : function () {
            var window_height = $(window).height();
            var header_height = $('.main-header').outerHeight();
            var footer_height = $('.main-footer').outerHeight();
            $("#mainFrame").height(window_height - header_height - footer_height-10);
        }
    }
})();

(function (win) {
debugger
    indexModel.initFrameHeight();
    win.onresize=function(){ indexModel.initFrameHeight();}
})(window);