var showGallery = function (galleryList){
    bigList = '';
    bigList += '<div class="row">'
    bigList +=    '<div class="col-lg-12 col-md-3 text-center galleryList">'
    bigList +=      '<a href="#"><i class="fa fa-home fa-3x" aria-hidden="true"></i></a>'
    bigList +=        '<h1> Art Galleries of Charleston</h1>'
    bigList +=    '<div class= "imgHov1">'
    bigList +=     '<img src="images/download.jpeg" />'
    bigList +=       '<p class="imgText1">Mary Martin Gallery has been selected as one of the top 25 galleries in America and as the best gallery in South Carolina in 2013, 2014 and 2015.</p>'
    bigList +=       '<p text-center>Mary Martin Gallery</p>'
    bigList +=    '</div>'
    bigList +=    '<div class= "imgHov2 ">'
    bigList +=      '<img src="images/download (1).jpeg" />'
    bigList +=       '<p class="imgText2">Atelier Gallery was founded in early 2008 by Gabrielle Egan. The gallery has become known for bringing innovative original artwork to local and national collectors alike. Atelier Gallery’s focus is to support and promote a variety of artists, while merging the classics with the moderns </p>'
    bigList +=       '<p text-center>Atelier Gallery</p>'
    bigList +=    '</div>'
    bigList +=    '<div class= "imgHov3 ">'
    bigList +=      '<img src="images/main.mv.jpeg" />'
    bigList +=       '<p class="imgText3">Martin Lawrence Galleries was founded in 1975 and operates 10 fine art galleries in the United States specializing in original paintings, sculpture and limited edition graphics.</p>'
    bigList +=       '<p text-center>Martin Gallery</p>'
    bigList +=    '</div>'
    bigList +=    '<div class= "imgHov4">'
    bigList +=      '<img src="images/robertLange.jpeg" />'
    bigList +=       '<p class="imgText4">Robert Lange Studios has been voted Best Art Gallery in Charleston for the last four years by the Charleston City Paper and Nathan Durfee has received the award for Best Visual Artist</p>'
    bigList +=       '<p text-center>Robert Lange Studios</p>'
    bigList +=    '</div>'
    bigList +=    '<div class= "imgHov5 ">'
    bigList +=      '<img src="images/images (2).jpeg" />'
    bigList +=       '<p class="imgText5">Horton Hayes Fine Art is newly located at 171 King Street in the heart of the art and antiques district of historic downtown Charleston, South Carolina. For over a decade, the Gallery has featured original works by locally and nationally recognized artists.</p>'
    bigList +=       '<p text-center>Horton Hayes Gallery </p>'
    bigList +=    '</div>'
    bigList +=    '<div class= "imgHov6 ">'
    bigList +=      '<img src="images/download (2).jpeg" />'
    bigList +=       '<p class="imgText6">HGordon Wheeler has painted for more than 30 years, developing his self-taught and highly personalized style. His paintings are full of vibrant color and are realistic in detail. He was commissioned to paint the famous Ocean Course at Kiawah Island for the official prints and posters of the 1991 Ryder Cup. </p>'
    bigList +=       '<p text-center> Gordon Wheeler </p>'
    bigList +=    '</div>'
    bigList +=    '<div class= "imgHov7">'
    bigList +=      '<img src="images/images.jpeg" />'
    bigList +=       '<p class="imgText7">University arts center showcasing emerging & established modern artists, films, lectures & more.</p>'
    bigList +=       '<p text-center>Halsey Institute</p>'
    bigList +=    '</div>'
    bigList +=    '<div class= "imgHov8 ">'
    bigList +=      '<img src="images/audubonimg.jpeg" />'
    bigList +=       '<p class="imgText8">TAudubon Galleries offers the finest examples of the work by famed natural history artist John James Audubon, including editions of his prints in both elephant folio and smaller formats, as well as his books and even original artwork.</p>'
    bigList +=       '<p text-center>Audobon Gallery </p>'
    bigList +=    '</div>'
    bigList +=   '</div>'
    bigList += '</div>'

    document.querySelector('#app-container').innerHTML = bigList

}
