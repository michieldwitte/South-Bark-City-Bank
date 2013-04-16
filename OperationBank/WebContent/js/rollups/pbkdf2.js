



<!DOCTYPE html>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
 
 <meta name="ROBOTS" content="NOARCHIVE">
 
 <link rel="icon" type="image/vnd.microsoft.icon" href="https://ssl.gstatic.com/codesite/ph/images/phosting.ico">
 
 
 <script type="text/javascript">
 
 
 
 
 var codesite_token = null;
 
 
 var CS_env = {"assetHostPath":"https://ssl.gstatic.com/codesite/ph","relativeBaseUrl":"","projectName":"crypto-js","assetVersionPath":"https://ssl.gstatic.com/codesite/ph/13714586478408612229","profileUrl":null,"domainName":null,"loggedInUserEmail":null,"token":null,"projectHomeUrl":"/p/crypto-js"};
 var _gaq = _gaq || [];
 _gaq.push(
 ['siteTracker._setAccount', 'UA-18071-1'],
 ['siteTracker._trackPageview']);
 
 _gaq.push(
 ['projectTracker._setAccount', 'UA-9709745-2'],
 ['projectTracker._trackPageview']);
 
 (function() {
 var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
 ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
 (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ga);
 })();
 
 </script>
 
 
 <title>pbkdf2.js - 
 crypto-js -
 
 
 JavaScript implementations of standard and secure cryptographic algorithms - Google Project Hosting
 </title>
 <link type="text/css" rel="stylesheet" href="https://ssl.gstatic.com/codesite/ph/13714586478408612229/css/core.css">
 
 <link type="text/css" rel="stylesheet" href="https://ssl.gstatic.com/codesite/ph/13714586478408612229/css/ph_detail.css" >
 
 
 <link type="text/css" rel="stylesheet" href="https://ssl.gstatic.com/codesite/ph/13714586478408612229/css/d_sb.css" >
 
 
 
<!--[if IE]>
 <link type="text/css" rel="stylesheet" href="https://ssl.gstatic.com/codesite/ph/13714586478408612229/css/d_ie.css" >
<![endif]-->
 <style type="text/css">
 .menuIcon.off { background: no-repeat url(https://ssl.gstatic.com/codesite/ph/images/dropdown_sprite.gif) 0 -42px }
 .menuIcon.on { background: no-repeat url(https://ssl.gstatic.com/codesite/ph/images/dropdown_sprite.gif) 0 -28px }
 .menuIcon.down { background: no-repeat url(https://ssl.gstatic.com/codesite/ph/images/dropdown_sprite.gif) 0 0; }
 
 
 
  tr.inline_comment {
 background: #fff;
 vertical-align: top;
 }
 div.draft, div.published {
 padding: .3em;
 border: 1px solid #999; 
 margin-bottom: .1em;
 font-family: arial, sans-serif;
 max-width: 60em;
 }
 div.draft {
 background: #ffa;
 } 
 div.published {
 background: #e5ecf9;
 }
 div.published .body, div.draft .body {
 padding: .5em .1em .1em .1em;
 max-width: 60em;
 white-space: pre-wrap;
 white-space: -moz-pre-wrap;
 white-space: -pre-wrap;
 white-space: -o-pre-wrap;
 word-wrap: break-word;
 font-size: 1em;
 }
 div.draft .actions {
 margin-left: 1em;
 font-size: 90%;
 }
 div.draft form {
 padding: .5em .5em .5em 0;
 }
 div.draft textarea, div.published textarea {
 width: 95%;
 height: 10em;
 font-family: arial, sans-serif;
 margin-bottom: .5em;
 }

 
 .nocursor, .nocursor td, .cursor_hidden, .cursor_hidden td {
 background-color: white;
 height: 2px;
 }
 .cursor, .cursor td {
 background-color: darkblue;
 height: 2px;
 display: '';
 }
 
 
.list {
 border: 1px solid white;
 border-bottom: 0;
}

 
 </style>
</head>
<body class="t4">
<script type="text/javascript">
 window.___gcfg = {lang: 'en'};
 (function() 
 {var po = document.createElement("script");
 po.type = "text/javascript"; po.async = true;po.src = "https://apis.google.com/js/plusone.js";
 var s = document.getElementsByTagName("script")[0];
 s.parentNode.insertBefore(po, s);
 })();
</script>
<div class="headbg">

 <div id="gaia">
 

 <span>
 
 
 <a href="#" id="projects-dropdown" onclick="return false;"><u>My favorites</u> <small>&#9660;</small></a>
 | <a href="https://www.google.com/accounts/ServiceLogin?service=code&amp;ltmpl=phosting&amp;continue=https%3A%2F%2Fcode.google.com%2Fp%2Fcrypto-js%2Fsource%2Fbrowse%2Ftags%2F3.1.2%2Fsrc%2Fpbkdf2.js&amp;followup=https%3A%2F%2Fcode.google.com%2Fp%2Fcrypto-js%2Fsource%2Fbrowse%2Ftags%2F3.1.2%2Fsrc%2Fpbkdf2.js" onclick="_CS_click('/gb/ph/signin');"><u>Sign in</u></a>
 
 </span>

 </div>

 <div class="gbh" style="left: 0pt;"></div>
 <div class="gbh" style="right: 0pt;"></div>
 
 
 <div style="height: 1px"></div>
<!--[if lte IE 7]>
<div style="text-align:center;">
Your version of Internet Explorer is not supported. Try a browser that
contributes to open source, such as <a href="http://www.firefox.com">Firefox</a>,
<a href="http://www.google.com/chrome">Google Chrome</a>, or
<a href="http://code.google.com/chrome/chromeframe/">Google Chrome Frame</a>.
</div>
<![endif]-->



 <table style="padding:0px; margin: 0px 0px 10px 0px; width:100%" cellpadding="0" cellspacing="0"
 itemscope itemtype="http://schema.org/CreativeWork">
 <tr style="height: 58px;">
 
 
 
 <td id="plogo">
 <link itemprop="url" href="/p/crypto-js">
 <a href="/p/crypto-js/">
 
 <img src="https://ssl.gstatic.com/codesite/ph/images/defaultlogo.png" alt="Logo" itemprop="image">
 
 </a>
 </td>
 
 <td style="padding-left: 0.5em">
 
 <div id="pname">
 <a href="/p/crypto-js/"><span itemprop="name">crypto-js</span></a>
 </div>
 
 <div id="psum">
 <a id="project_summary_link"
 href="/p/crypto-js/"><span itemprop="description">JavaScript implementations of standard and secure cryptographic algorithms</span></a>
 
 </div>
 
 
 </td>
 <td style="white-space:nowrap;text-align:right; vertical-align:bottom;">
 
 <form action="/hosting/search">
 <input size="30" name="q" value="" type="text">
 
 <input type="submit" name="projectsearch" value="Search projects" >
 </form>
 
 </tr>
 </table>

</div>

 
<div id="mt" class="gtb"> 
 <a href="/p/crypto-js/" class="tab ">Project&nbsp;Home</a>
 
 
 
 
 <a href="/p/crypto-js/downloads/list" class="tab ">Downloads</a>
 
 
 
 
 
 <a href="/p/crypto-js/w/list" class="tab ">Wiki</a>
 
 
 
 
 
 <a href="/p/crypto-js/issues/list"
 class="tab ">Issues</a>
 
 
 
 
 
 <a href="/p/crypto-js/source/checkout"
 class="tab active">Source</a>
 
 
 
 
 
 
 
 
 <div class=gtbc></div>
</div>
<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0" class="st">
 <tr>
 
 
 
 
 
 
 <td class="subt">
 <div class="st2">
 <div class="isf">
 
 


 <span class="inst1"><a href="/p/crypto-js/source/checkout">Checkout</a></span> &nbsp;
 <span class="inst2"><a href="/p/crypto-js/source/browse/tags/3.1.2/src">Browse</a></span> &nbsp;
 <span class="inst3"><a href="/p/crypto-js/source/list">Changes</a></span> &nbsp;
 
 
 
 
 
 
 
 </form>
 <script type="text/javascript">
 
 function codesearchQuery(form) {
 var query = document.getElementById('q').value;
 if (query) { form.action += '%20' + query; }
 }
 </script>
 </div>
</div>

 </td>
 
 
 
 <td align="right" valign="top" class="bevel-right"></td>
 </tr>
</table>


<script type="text/javascript">
 var cancelBubble = false;
 function _go(url) { document.location = url; }
</script>
<div id="maincol"
 
>

 




<div class="expand">
<div id="colcontrol">
<style type="text/css">
 #file_flipper { white-space: nowrap; padding-right: 2em; }
 #file_flipper.hidden { display: none; }
 #file_flipper .pagelink { color: #0000CC; text-decoration: underline; }
 #file_flipper #visiblefiles { padding-left: 0.5em; padding-right: 0.5em; }
</style>
<table id="nav_and_rev" class="list"
 cellpadding="0" cellspacing="0" width="100%">
 <tr>
 
 <td nowrap="nowrap" class="src_crumbs src_nav" width="33%">
 <strong class="src_nav">Source path:&nbsp;</strong>
 <span id="crumb_root">
 
 <a href="/p/crypto-js/source/browse/">svn</a>/&nbsp;</span>
 <span id="crumb_links" class="ifClosed"><a href="/p/crypto-js/source/browse/tags/">tags</a><span class="sp">/&nbsp;</span><a href="/p/crypto-js/source/browse/tags/3.1.2/">3.1.2</a><span class="sp">/&nbsp;</span><a href="/p/crypto-js/source/browse/tags/3.1.2/src/">src</a><span class="sp">/&nbsp;</span>pbkdf2.js</span>
 
 


 </td>
 
 
 <td nowrap="nowrap" width="33%" align="right">
 <table cellpadding="0" cellspacing="0" style="font-size: 100%"><tr>
 
 
 <td class="flipper">
 <ul class="leftside">
 
 <li><a href="/p/crypto-js/source/browse/branches/3.x/src/pbkdf2.js?r=607" title="Previous">&lsaquo;r607</a></li>
 
 </ul>
 </td>
 
 <td class="flipper"><b>r664</b></td>
 
 </tr></table>
 </td> 
 </tr>
</table>

<div class="fc">
 
 
 
<style type="text/css">
.undermouse span {
 background-image: url(https://ssl.gstatic.com/codesite/ph/images/comments.gif); }
</style>
<table class="opened" id="review_comment_area"
><tr>
<td id="nums">
<pre><table width="100%"><tr class="nocursor"><td></td></tr></table></pre>
<pre><table width="100%" id="nums_table_0"><tr id="gr_svn664_1"

><td id="1"><a href="#1">1</a></td></tr
><tr id="gr_svn664_2"

><td id="2"><a href="#2">2</a></td></tr
><tr id="gr_svn664_3"

><td id="3"><a href="#3">3</a></td></tr
><tr id="gr_svn664_4"

><td id="4"><a href="#4">4</a></td></tr
><tr id="gr_svn664_5"

><td id="5"><a href="#5">5</a></td></tr
><tr id="gr_svn664_6"

><td id="6"><a href="#6">6</a></td></tr
><tr id="gr_svn664_7"

><td id="7"><a href="#7">7</a></td></tr
><tr id="gr_svn664_8"

><td id="8"><a href="#8">8</a></td></tr
><tr id="gr_svn664_9"

><td id="9"><a href="#9">9</a></td></tr
><tr id="gr_svn664_10"

><td id="10"><a href="#10">10</a></td></tr
><tr id="gr_svn664_11"

><td id="11"><a href="#11">11</a></td></tr
><tr id="gr_svn664_12"

><td id="12"><a href="#12">12</a></td></tr
><tr id="gr_svn664_13"

><td id="13"><a href="#13">13</a></td></tr
><tr id="gr_svn664_14"

><td id="14"><a href="#14">14</a></td></tr
><tr id="gr_svn664_15"

><td id="15"><a href="#15">15</a></td></tr
><tr id="gr_svn664_16"

><td id="16"><a href="#16">16</a></td></tr
><tr id="gr_svn664_17"

><td id="17"><a href="#17">17</a></td></tr
><tr id="gr_svn664_18"

><td id="18"><a href="#18">18</a></td></tr
><tr id="gr_svn664_19"

><td id="19"><a href="#19">19</a></td></tr
><tr id="gr_svn664_20"

><td id="20"><a href="#20">20</a></td></tr
><tr id="gr_svn664_21"

><td id="21"><a href="#21">21</a></td></tr
><tr id="gr_svn664_22"

><td id="22"><a href="#22">22</a></td></tr
><tr id="gr_svn664_23"

><td id="23"><a href="#23">23</a></td></tr
><tr id="gr_svn664_24"

><td id="24"><a href="#24">24</a></td></tr
><tr id="gr_svn664_25"

><td id="25"><a href="#25">25</a></td></tr
><tr id="gr_svn664_26"

><td id="26"><a href="#26">26</a></td></tr
><tr id="gr_svn664_27"

><td id="27"><a href="#27">27</a></td></tr
><tr id="gr_svn664_28"

><td id="28"><a href="#28">28</a></td></tr
><tr id="gr_svn664_29"

><td id="29"><a href="#29">29</a></td></tr
><tr id="gr_svn664_30"

><td id="30"><a href="#30">30</a></td></tr
><tr id="gr_svn664_31"

><td id="31"><a href="#31">31</a></td></tr
><tr id="gr_svn664_32"

><td id="32"><a href="#32">32</a></td></tr
><tr id="gr_svn664_33"

><td id="33"><a href="#33">33</a></td></tr
><tr id="gr_svn664_34"

><td id="34"><a href="#34">34</a></td></tr
><tr id="gr_svn664_35"

><td id="35"><a href="#35">35</a></td></tr
><tr id="gr_svn664_36"

><td id="36"><a href="#36">36</a></td></tr
><tr id="gr_svn664_37"

><td id="37"><a href="#37">37</a></td></tr
><tr id="gr_svn664_38"

><td id="38"><a href="#38">38</a></td></tr
><tr id="gr_svn664_39"

><td id="39"><a href="#39">39</a></td></tr
><tr id="gr_svn664_40"

><td id="40"><a href="#40">40</a></td></tr
><tr id="gr_svn664_41"

><td id="41"><a href="#41">41</a></td></tr
><tr id="gr_svn664_42"

><td id="42"><a href="#42">42</a></td></tr
><tr id="gr_svn664_43"

><td id="43"><a href="#43">43</a></td></tr
><tr id="gr_svn664_44"

><td id="44"><a href="#44">44</a></td></tr
><tr id="gr_svn664_45"

><td id="45"><a href="#45">45</a></td></tr
><tr id="gr_svn664_46"

><td id="46"><a href="#46">46</a></td></tr
><tr id="gr_svn664_47"

><td id="47"><a href="#47">47</a></td></tr
><tr id="gr_svn664_48"

><td id="48"><a href="#48">48</a></td></tr
><tr id="gr_svn664_49"

><td id="49"><a href="#49">49</a></td></tr
><tr id="gr_svn664_50"

><td id="50"><a href="#50">50</a></td></tr
><tr id="gr_svn664_51"

><td id="51"><a href="#51">51</a></td></tr
><tr id="gr_svn664_52"

><td id="52"><a href="#52">52</a></td></tr
><tr id="gr_svn664_53"

><td id="53"><a href="#53">53</a></td></tr
><tr id="gr_svn664_54"

><td id="54"><a href="#54">54</a></td></tr
><tr id="gr_svn664_55"

><td id="55"><a href="#55">55</a></td></tr
><tr id="gr_svn664_56"

><td id="56"><a href="#56">56</a></td></tr
><tr id="gr_svn664_57"

><td id="57"><a href="#57">57</a></td></tr
><tr id="gr_svn664_58"

><td id="58"><a href="#58">58</a></td></tr
><tr id="gr_svn664_59"

><td id="59"><a href="#59">59</a></td></tr
><tr id="gr_svn664_60"

><td id="60"><a href="#60">60</a></td></tr
><tr id="gr_svn664_61"

><td id="61"><a href="#61">61</a></td></tr
><tr id="gr_svn664_62"

><td id="62"><a href="#62">62</a></td></tr
><tr id="gr_svn664_63"

><td id="63"><a href="#63">63</a></td></tr
><tr id="gr_svn664_64"

><td id="64"><a href="#64">64</a></td></tr
><tr id="gr_svn664_65"

><td id="65"><a href="#65">65</a></td></tr
><tr id="gr_svn664_66"

><td id="66"><a href="#66">66</a></td></tr
><tr id="gr_svn664_67"

><td id="67"><a href="#67">67</a></td></tr
><tr id="gr_svn664_68"

><td id="68"><a href="#68">68</a></td></tr
><tr id="gr_svn664_69"

><td id="69"><a href="#69">69</a></td></tr
><tr id="gr_svn664_70"

><td id="70"><a href="#70">70</a></td></tr
><tr id="gr_svn664_71"

><td id="71"><a href="#71">71</a></td></tr
><tr id="gr_svn664_72"

><td id="72"><a href="#72">72</a></td></tr
><tr id="gr_svn664_73"

><td id="73"><a href="#73">73</a></td></tr
><tr id="gr_svn664_74"

><td id="74"><a href="#74">74</a></td></tr
><tr id="gr_svn664_75"

><td id="75"><a href="#75">75</a></td></tr
><tr id="gr_svn664_76"

><td id="76"><a href="#76">76</a></td></tr
><tr id="gr_svn664_77"

><td id="77"><a href="#77">77</a></td></tr
><tr id="gr_svn664_78"

><td id="78"><a href="#78">78</a></td></tr
><tr id="gr_svn664_79"

><td id="79"><a href="#79">79</a></td></tr
><tr id="gr_svn664_80"

><td id="80"><a href="#80">80</a></td></tr
><tr id="gr_svn664_81"

><td id="81"><a href="#81">81</a></td></tr
><tr id="gr_svn664_82"

><td id="82"><a href="#82">82</a></td></tr
><tr id="gr_svn664_83"

><td id="83"><a href="#83">83</a></td></tr
><tr id="gr_svn664_84"

><td id="84"><a href="#84">84</a></td></tr
><tr id="gr_svn664_85"

><td id="85"><a href="#85">85</a></td></tr
><tr id="gr_svn664_86"

><td id="86"><a href="#86">86</a></td></tr
><tr id="gr_svn664_87"

><td id="87"><a href="#87">87</a></td></tr
><tr id="gr_svn664_88"

><td id="88"><a href="#88">88</a></td></tr
><tr id="gr_svn664_89"

><td id="89"><a href="#89">89</a></td></tr
><tr id="gr_svn664_90"

><td id="90"><a href="#90">90</a></td></tr
><tr id="gr_svn664_91"

><td id="91"><a href="#91">91</a></td></tr
><tr id="gr_svn664_92"

><td id="92"><a href="#92">92</a></td></tr
><tr id="gr_svn664_93"

><td id="93"><a href="#93">93</a></td></tr
><tr id="gr_svn664_94"

><td id="94"><a href="#94">94</a></td></tr
><tr id="gr_svn664_95"

><td id="95"><a href="#95">95</a></td></tr
><tr id="gr_svn664_96"

><td id="96"><a href="#96">96</a></td></tr
><tr id="gr_svn664_97"

><td id="97"><a href="#97">97</a></td></tr
><tr id="gr_svn664_98"

><td id="98"><a href="#98">98</a></td></tr
><tr id="gr_svn664_99"

><td id="99"><a href="#99">99</a></td></tr
><tr id="gr_svn664_100"

><td id="100"><a href="#100">100</a></td></tr
><tr id="gr_svn664_101"

><td id="101"><a href="#101">101</a></td></tr
><tr id="gr_svn664_102"

><td id="102"><a href="#102">102</a></td></tr
><tr id="gr_svn664_103"

><td id="103"><a href="#103">103</a></td></tr
><tr id="gr_svn664_104"

><td id="104"><a href="#104">104</a></td></tr
><tr id="gr_svn664_105"

><td id="105"><a href="#105">105</a></td></tr
><tr id="gr_svn664_106"

><td id="106"><a href="#106">106</a></td></tr
><tr id="gr_svn664_107"

><td id="107"><a href="#107">107</a></td></tr
><tr id="gr_svn664_108"

><td id="108"><a href="#108">108</a></td></tr
><tr id="gr_svn664_109"

><td id="109"><a href="#109">109</a></td></tr
><tr id="gr_svn664_110"

><td id="110"><a href="#110">110</a></td></tr
><tr id="gr_svn664_111"

><td id="111"><a href="#111">111</a></td></tr
><tr id="gr_svn664_112"

><td id="112"><a href="#112">112</a></td></tr
><tr id="gr_svn664_113"

><td id="113"><a href="#113">113</a></td></tr
><tr id="gr_svn664_114"

><td id="114"><a href="#114">114</a></td></tr
><tr id="gr_svn664_115"

><td id="115"><a href="#115">115</a></td></tr
><tr id="gr_svn664_116"

><td id="116"><a href="#116">116</a></td></tr
><tr id="gr_svn664_117"

><td id="117"><a href="#117">117</a></td></tr
><tr id="gr_svn664_118"

><td id="118"><a href="#118">118</a></td></tr
><tr id="gr_svn664_119"

><td id="119"><a href="#119">119</a></td></tr
><tr id="gr_svn664_120"

><td id="120"><a href="#120">120</a></td></tr
><tr id="gr_svn664_121"

><td id="121"><a href="#121">121</a></td></tr
><tr id="gr_svn664_122"

><td id="122"><a href="#122">122</a></td></tr
><tr id="gr_svn664_123"

><td id="123"><a href="#123">123</a></td></tr
><tr id="gr_svn664_124"

><td id="124"><a href="#124">124</a></td></tr
><tr id="gr_svn664_125"

><td id="125"><a href="#125">125</a></td></tr
></table></pre>
<pre><table width="100%"><tr class="nocursor"><td></td></tr></table></pre>
</td>
<td id="lines">
<pre><table width="100%"><tr class="cursor_stop cursor_hidden"><td></td></tr></table></pre>
<pre class="prettyprint lang-js"><table id="src_table_0"><tr
id=sl_svn664_1

><td class="source">(function () {<br></td></tr
><tr
id=sl_svn664_2

><td class="source">    // Shortcuts<br></td></tr
><tr
id=sl_svn664_3

><td class="source">    var C = CryptoJS;<br></td></tr
><tr
id=sl_svn664_4

><td class="source">    var C_lib = C.lib;<br></td></tr
><tr
id=sl_svn664_5

><td class="source">    var Base = C_lib.Base;<br></td></tr
><tr
id=sl_svn664_6

><td class="source">    var WordArray = C_lib.WordArray;<br></td></tr
><tr
id=sl_svn664_7

><td class="source">    var C_algo = C.algo;<br></td></tr
><tr
id=sl_svn664_8

><td class="source">    var SHA1 = C_algo.SHA1;<br></td></tr
><tr
id=sl_svn664_9

><td class="source">    var HMAC = C_algo.HMAC;<br></td></tr
><tr
id=sl_svn664_10

><td class="source"><br></td></tr
><tr
id=sl_svn664_11

><td class="source">    /**<br></td></tr
><tr
id=sl_svn664_12

><td class="source">     * Password-Based Key Derivation Function 2 algorithm.<br></td></tr
><tr
id=sl_svn664_13

><td class="source">     */<br></td></tr
><tr
id=sl_svn664_14

><td class="source">    var PBKDF2 = C_algo.PBKDF2 = Base.extend({<br></td></tr
><tr
id=sl_svn664_15

><td class="source">        /**<br></td></tr
><tr
id=sl_svn664_16

><td class="source">         * Configuration options.<br></td></tr
><tr
id=sl_svn664_17

><td class="source">         *<br></td></tr
><tr
id=sl_svn664_18

><td class="source">         * @property {number} keySize The key size in words to generate. Default: 4 (128 bits)<br></td></tr
><tr
id=sl_svn664_19

><td class="source">         * @property {Hasher} hasher The hasher to use. Default: SHA1<br></td></tr
><tr
id=sl_svn664_20

><td class="source">         * @property {number} iterations The number of iterations to perform. Default: 1<br></td></tr
><tr
id=sl_svn664_21

><td class="source">         */<br></td></tr
><tr
id=sl_svn664_22

><td class="source">        cfg: Base.extend({<br></td></tr
><tr
id=sl_svn664_23

><td class="source">            keySize: 128/32,<br></td></tr
><tr
id=sl_svn664_24

><td class="source">            hasher: SHA1,<br></td></tr
><tr
id=sl_svn664_25

><td class="source">            iterations: 1<br></td></tr
><tr
id=sl_svn664_26

><td class="source">        }),<br></td></tr
><tr
id=sl_svn664_27

><td class="source"><br></td></tr
><tr
id=sl_svn664_28

><td class="source">        /**<br></td></tr
><tr
id=sl_svn664_29

><td class="source">         * Initializes a newly created key derivation function.<br></td></tr
><tr
id=sl_svn664_30

><td class="source">         *<br></td></tr
><tr
id=sl_svn664_31

><td class="source">         * @param {Object} cfg (Optional) The configuration options to use for the derivation.<br></td></tr
><tr
id=sl_svn664_32

><td class="source">         *<br></td></tr
><tr
id=sl_svn664_33

><td class="source">         * @example<br></td></tr
><tr
id=sl_svn664_34

><td class="source">         *<br></td></tr
><tr
id=sl_svn664_35

><td class="source">         *     var kdf = CryptoJS.algo.PBKDF2.create();<br></td></tr
><tr
id=sl_svn664_36

><td class="source">         *     var kdf = CryptoJS.algo.PBKDF2.create({ keySize: 8 });<br></td></tr
><tr
id=sl_svn664_37

><td class="source">         *     var kdf = CryptoJS.algo.PBKDF2.create({ keySize: 8, iterations: 1000 });<br></td></tr
><tr
id=sl_svn664_38

><td class="source">         */<br></td></tr
><tr
id=sl_svn664_39

><td class="source">        init: function (cfg) {<br></td></tr
><tr
id=sl_svn664_40

><td class="source">            this.cfg = this.cfg.extend(cfg);<br></td></tr
><tr
id=sl_svn664_41

><td class="source">        },<br></td></tr
><tr
id=sl_svn664_42

><td class="source"><br></td></tr
><tr
id=sl_svn664_43

><td class="source">        /**<br></td></tr
><tr
id=sl_svn664_44

><td class="source">         * Computes the Password-Based Key Derivation Function 2.<br></td></tr
><tr
id=sl_svn664_45

><td class="source">         *<br></td></tr
><tr
id=sl_svn664_46

><td class="source">         * @param {WordArray|string} password The password.<br></td></tr
><tr
id=sl_svn664_47

><td class="source">         * @param {WordArray|string} salt A salt.<br></td></tr
><tr
id=sl_svn664_48

><td class="source">         *<br></td></tr
><tr
id=sl_svn664_49

><td class="source">         * @return {WordArray} The derived key.<br></td></tr
><tr
id=sl_svn664_50

><td class="source">         *<br></td></tr
><tr
id=sl_svn664_51

><td class="source">         * @example<br></td></tr
><tr
id=sl_svn664_52

><td class="source">         *<br></td></tr
><tr
id=sl_svn664_53

><td class="source">         *     var key = kdf.compute(password, salt);<br></td></tr
><tr
id=sl_svn664_54

><td class="source">         */<br></td></tr
><tr
id=sl_svn664_55

><td class="source">        compute: function (password, salt) {<br></td></tr
><tr
id=sl_svn664_56

><td class="source">            // Shortcut<br></td></tr
><tr
id=sl_svn664_57

><td class="source">            var cfg = this.cfg;<br></td></tr
><tr
id=sl_svn664_58

><td class="source"><br></td></tr
><tr
id=sl_svn664_59

><td class="source">            // Init HMAC<br></td></tr
><tr
id=sl_svn664_60

><td class="source">            var hmac = HMAC.create(cfg.hasher, password);<br></td></tr
><tr
id=sl_svn664_61

><td class="source"><br></td></tr
><tr
id=sl_svn664_62

><td class="source">            // Initial values<br></td></tr
><tr
id=sl_svn664_63

><td class="source">            var derivedKey = WordArray.create();<br></td></tr
><tr
id=sl_svn664_64

><td class="source">            var blockIndex = WordArray.create([0x00000001]);<br></td></tr
><tr
id=sl_svn664_65

><td class="source"><br></td></tr
><tr
id=sl_svn664_66

><td class="source">            // Shortcuts<br></td></tr
><tr
id=sl_svn664_67

><td class="source">            var derivedKeyWords = derivedKey.words;<br></td></tr
><tr
id=sl_svn664_68

><td class="source">            var blockIndexWords = blockIndex.words;<br></td></tr
><tr
id=sl_svn664_69

><td class="source">            var keySize = cfg.keySize;<br></td></tr
><tr
id=sl_svn664_70

><td class="source">            var iterations = cfg.iterations;<br></td></tr
><tr
id=sl_svn664_71

><td class="source"><br></td></tr
><tr
id=sl_svn664_72

><td class="source">            // Generate key<br></td></tr
><tr
id=sl_svn664_73

><td class="source">            while (derivedKeyWords.length &lt; keySize) {<br></td></tr
><tr
id=sl_svn664_74

><td class="source">                var block = hmac.update(salt).finalize(blockIndex);<br></td></tr
><tr
id=sl_svn664_75

><td class="source">                hmac.reset();<br></td></tr
><tr
id=sl_svn664_76

><td class="source"><br></td></tr
><tr
id=sl_svn664_77

><td class="source">                // Shortcuts<br></td></tr
><tr
id=sl_svn664_78

><td class="source">                var blockWords = block.words;<br></td></tr
><tr
id=sl_svn664_79

><td class="source">                var blockWordsLength = blockWords.length;<br></td></tr
><tr
id=sl_svn664_80

><td class="source"><br></td></tr
><tr
id=sl_svn664_81

><td class="source">                // Iterations<br></td></tr
><tr
id=sl_svn664_82

><td class="source">                var intermediate = block;<br></td></tr
><tr
id=sl_svn664_83

><td class="source">                for (var i = 1; i &lt; iterations; i++) {<br></td></tr
><tr
id=sl_svn664_84

><td class="source">                    intermediate = hmac.finalize(intermediate);<br></td></tr
><tr
id=sl_svn664_85

><td class="source">                    hmac.reset();<br></td></tr
><tr
id=sl_svn664_86

><td class="source"><br></td></tr
><tr
id=sl_svn664_87

><td class="source">                    // Shortcut<br></td></tr
><tr
id=sl_svn664_88

><td class="source">                    var intermediateWords = intermediate.words;<br></td></tr
><tr
id=sl_svn664_89

><td class="source"><br></td></tr
><tr
id=sl_svn664_90

><td class="source">                    // XOR intermediate with block<br></td></tr
><tr
id=sl_svn664_91

><td class="source">                    for (var j = 0; j &lt; blockWordsLength; j++) {<br></td></tr
><tr
id=sl_svn664_92

><td class="source">                        blockWords[j] ^= intermediateWords[j];<br></td></tr
><tr
id=sl_svn664_93

><td class="source">                    }<br></td></tr
><tr
id=sl_svn664_94

><td class="source">                }<br></td></tr
><tr
id=sl_svn664_95

><td class="source"><br></td></tr
><tr
id=sl_svn664_96

><td class="source">                derivedKey.concat(block);<br></td></tr
><tr
id=sl_svn664_97

><td class="source">                blockIndexWords[0]++;<br></td></tr
><tr
id=sl_svn664_98

><td class="source">            }<br></td></tr
><tr
id=sl_svn664_99

><td class="source">            derivedKey.sigBytes = keySize * 4;<br></td></tr
><tr
id=sl_svn664_100

><td class="source"><br></td></tr
><tr
id=sl_svn664_101

><td class="source">            return derivedKey;<br></td></tr
><tr
id=sl_svn664_102

><td class="source">        }<br></td></tr
><tr
id=sl_svn664_103

><td class="source">    });<br></td></tr
><tr
id=sl_svn664_104

><td class="source"><br></td></tr
><tr
id=sl_svn664_105

><td class="source">    /**<br></td></tr
><tr
id=sl_svn664_106

><td class="source">     * Computes the Password-Based Key Derivation Function 2.<br></td></tr
><tr
id=sl_svn664_107

><td class="source">     *<br></td></tr
><tr
id=sl_svn664_108

><td class="source">     * @param {WordArray|string} password The password.<br></td></tr
><tr
id=sl_svn664_109

><td class="source">     * @param {WordArray|string} salt A salt.<br></td></tr
><tr
id=sl_svn664_110

><td class="source">     * @param {Object} cfg (Optional) The configuration options to use for this computation.<br></td></tr
><tr
id=sl_svn664_111

><td class="source">     *<br></td></tr
><tr
id=sl_svn664_112

><td class="source">     * @return {WordArray} The derived key.<br></td></tr
><tr
id=sl_svn664_113

><td class="source">     *<br></td></tr
><tr
id=sl_svn664_114

><td class="source">     * @static<br></td></tr
><tr
id=sl_svn664_115

><td class="source">     *<br></td></tr
><tr
id=sl_svn664_116

><td class="source">     * @example<br></td></tr
><tr
id=sl_svn664_117

><td class="source">     *<br></td></tr
><tr
id=sl_svn664_118

><td class="source">     *     var key = CryptoJS.PBKDF2(password, salt);<br></td></tr
><tr
id=sl_svn664_119

><td class="source">     *     var key = CryptoJS.PBKDF2(password, salt, { keySize: 8 });<br></td></tr
><tr
id=sl_svn664_120

><td class="source">     *     var key = CryptoJS.PBKDF2(password, salt, { keySize: 8, iterations: 1000 });<br></td></tr
><tr
id=sl_svn664_121

><td class="source">     */<br></td></tr
><tr
id=sl_svn664_122

><td class="source">    C.PBKDF2 = function (password, salt, cfg) {<br></td></tr
><tr
id=sl_svn664_123

><td class="source">        return PBKDF2.create(cfg).compute(password, salt);<br></td></tr
><tr
id=sl_svn664_124

><td class="source">    };<br></td></tr
><tr
id=sl_svn664_125

><td class="source">}());<br></td></tr
></table></pre>
<pre><table width="100%"><tr class="cursor_stop cursor_hidden"><td></td></tr></table></pre>
</td>
</tr></table>

 
<script type="text/javascript">
 var lineNumUnderMouse = -1;
 
 function gutterOver(num) {
 gutterOut();
 var newTR = document.getElementById('gr_svn664_' + num);
 if (newTR) {
 newTR.className = 'undermouse';
 }
 lineNumUnderMouse = num;
 }
 function gutterOut() {
 if (lineNumUnderMouse != -1) {
 var oldTR = document.getElementById(
 'gr_svn664_' + lineNumUnderMouse);
 if (oldTR) {
 oldTR.className = '';
 }
 lineNumUnderMouse = -1;
 }
 }
 var numsGenState = {table_base_id: 'nums_table_'};
 var srcGenState = {table_base_id: 'src_table_'};
 var alignerRunning = false;
 var startOver = false;
 function setLineNumberHeights() {
 if (alignerRunning) {
 startOver = true;
 return;
 }
 numsGenState.chunk_id = 0;
 numsGenState.table = document.getElementById('nums_table_0');
 numsGenState.row_num = 0;
 if (!numsGenState.table) {
 return; // Silently exit if no file is present.
 }
 srcGenState.chunk_id = 0;
 srcGenState.table = document.getElementById('src_table_0');
 srcGenState.row_num = 0;
 alignerRunning = true;
 continueToSetLineNumberHeights();
 }
 function rowGenerator(genState) {
 if (genState.row_num < genState.table.rows.length) {
 var currentRow = genState.table.rows[genState.row_num];
 genState.row_num++;
 return currentRow;
 }
 var newTable = document.getElementById(
 genState.table_base_id + (genState.chunk_id + 1));
 if (newTable) {
 genState.chunk_id++;
 genState.row_num = 0;
 genState.table = newTable;
 return genState.table.rows[0];
 }
 return null;
 }
 var MAX_ROWS_PER_PASS = 1000;
 function continueToSetLineNumberHeights() {
 var rowsInThisPass = 0;
 var numRow = 1;
 var srcRow = 1;
 while (numRow && srcRow && rowsInThisPass < MAX_ROWS_PER_PASS) {
 numRow = rowGenerator(numsGenState);
 srcRow = rowGenerator(srcGenState);
 rowsInThisPass++;
 if (numRow && srcRow) {
 if (numRow.offsetHeight != srcRow.offsetHeight) {
 numRow.firstChild.style.height = srcRow.offsetHeight + 'px';
 }
 }
 }
 if (rowsInThisPass >= MAX_ROWS_PER_PASS) {
 setTimeout(continueToSetLineNumberHeights, 10);
 } else {
 alignerRunning = false;
 if (startOver) {
 startOver = false;
 setTimeout(setLineNumberHeights, 500);
 }
 }
 }
 function initLineNumberHeights() {
 // Do 2 complete passes, because there can be races
 // between this code and prettify.
 startOver = true;
 setTimeout(setLineNumberHeights, 250);
 window.onresize = setLineNumberHeights;
 }
 initLineNumberHeights();
</script>

 
 
 <div id="log">
 <div style="text-align:right">
 <a class="ifCollapse" href="#" onclick="_toggleMeta(this); return false">Show details</a>
 <a class="ifExpand" href="#" onclick="_toggleMeta(this); return false">Hide details</a>
 </div>
 <div class="ifExpand">
 
 
 <div class="pmeta_bubble_bg" style="border:1px solid white">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <div id="changelog">
 <p>Change log</p>
 <div>
 <a href="/p/crypto-js/source/detail?spec=svn664&amp;r=611">r611</a>
 by Jeff.Mott.OR
 on Jan 15, 2013
 &nbsp; <a href="/p/crypto-js/source/diff?spec=svn664&r=611&amp;format=side&amp;path=/tags/3.1.2/src/pbkdf2.js&amp;old_path=/branches/3.x/src/pbkdf2.js&amp;old=554">Diff</a>
 </div>
 <pre>Tagged version 3.1.2.</pre>
 </div>
 
 
 
 
 
 
 <script type="text/javascript">
 var detail_url = '/p/crypto-js/source/detail?r=611&spec=svn664';
 var publish_url = '/p/crypto-js/source/detail?r=611&spec=svn664#publish';
 // describe the paths of this revision in javascript.
 var changed_paths = [];
 var changed_urls = [];
 
 changed_paths.push('/tags/3.1.2');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/aes-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/aes-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/aes.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/aes.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/cipher-core-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/cipher-core-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/cipher-core.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/cipher-core.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/core-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/core-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/core.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/core.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/enc-base64-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/enc-base64-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/enc-base64.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/enc-base64.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/enc-utf16-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/enc-utf16-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/enc-utf16.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/enc-utf16.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/evpkdf-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/evpkdf-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/evpkdf.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/evpkdf.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/format-hex-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/format-hex-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/format-hex.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/format-hex.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/hmac-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/hmac-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/hmac.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/hmac.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/lib-typedarrays-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/lib-typedarrays-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/lib-typedarrays.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/lib-typedarrays.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/md5-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/md5-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/md5.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/md5.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/mode-cfb-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-cfb-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/mode-cfb.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-cfb.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/mode-ctr-gladman-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ctr-gladman-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/mode-ctr-gladman.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ctr-gladman.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/mode-ctr-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ctr-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/mode-ctr.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ctr.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/mode-ecb-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ecb-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/mode-ecb.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ecb.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/mode-ofb-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ofb-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/mode-ofb.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ofb.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pad-ansix923-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-ansix923-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pad-ansix923.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-ansix923.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pad-iso10126-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-iso10126-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pad-iso10126.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-iso10126.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pad-iso97971-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-iso97971-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pad-iso97971.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-iso97971.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pad-nopadding-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-nopadding-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pad-nopadding.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-nopadding.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pad-zeropadding-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-zeropadding-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pad-zeropadding.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-zeropadding.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pbkdf2-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pbkdf2-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/pbkdf2.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/pbkdf2.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/rabbit-legacy-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/rabbit-legacy-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/rabbit-legacy.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/rabbit-legacy.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/rabbit-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/rabbit-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/rabbit.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/rabbit.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/rc4-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/rc4-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/rc4.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/rc4.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/ripemd160-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/ripemd160-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/ripemd160.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/ripemd160.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha1-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha1-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha1.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha1.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha224-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha224-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha224.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha224.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha256-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha256-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha256.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha256.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha3-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha3-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha3.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha3.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha384-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha384-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha384.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha384.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha512-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha512-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/sha512.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/sha512.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/tripledes-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/tripledes-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/tripledes.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/tripledes.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/x64-core-min.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/x64-core-min.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/components/x64-core.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/components/x64-core.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/aes.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/aes.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/hmac-md5.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-md5.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/hmac-ripemd160.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-ripemd160.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/hmac-sha1.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha1.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/hmac-sha224.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha224.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/hmac-sha256.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha256.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/hmac-sha3.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha3.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/hmac-sha384.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha384.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/hmac-sha512.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha512.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/md5.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/md5.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/pbkdf2.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/pbkdf2.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/rabbit-legacy.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/rabbit-legacy.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/rabbit.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/rabbit.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/rc4.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/rc4.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/ripemd160.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/ripemd160.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/sha1.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha1.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/sha224.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha224.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/sha256.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha256.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/sha3.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha3.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/sha384.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha384.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/sha512.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha512.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/build/rollups/tripledes.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/build/rollups/tripledes.js?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/builder/build.yml');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/builder/build.yml?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/builder/copyright.php');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/builder/copyright.php?r\x3d611\x26spec\x3dsvn664');
 
 
 changed_paths.push('/tags/3.1.2/test/lib-typedarrays-test.js');
 changed_urls.push('/p/crypto-js/source/browse/tags/3.1.2/test/lib-typedarrays-test.js?r\x3d611\x26spec\x3dsvn664');
 
 
 function getCurrentPageIndex() {
 for (var i = 0; i < changed_paths.length; i++) {
 if (selected_path == changed_paths[i]) {
 return i;
 }
 }
 }
 function getNextPage() {
 var i = getCurrentPageIndex();
 if (i < changed_paths.length - 1) {
 return changed_urls[i + 1];
 }
 return null;
 }
 function getPreviousPage() {
 var i = getCurrentPageIndex();
 if (i > 0) {
 return changed_urls[i - 1];
 }
 return null;
 }
 function gotoNextPage() {
 var page = getNextPage();
 if (!page) {
 page = detail_url;
 }
 window.location = page;
 }
 function gotoPreviousPage() {
 var page = getPreviousPage();
 if (!page) {
 page = detail_url;
 }
 window.location = page;
 }
 function gotoDetailPage() {
 window.location = detail_url;
 }
 function gotoPublishPage() {
 window.location = publish_url;
 }
</script>

 
 <style type="text/css">
 #review_nav {
 border-top: 3px solid white;
 padding-top: 6px;
 margin-top: 1em;
 }
 #review_nav td {
 vertical-align: middle;
 }
 #review_nav select {
 margin: .5em 0;
 }
 </style>
 <div id="review_nav">
 <table><tr><td>Go to:&nbsp;</td><td>
 <select name="files_in_rev" onchange="window.location=this.value">
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2?r=611&amp;spec=svn664"
 
 >/tags/3.1.2</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/components</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/aes-min.js?r=611&amp;spec=svn664"
 
 >....1.2/build/components/aes-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/aes.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/components/aes.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/cipher-core-min.js?r=611&amp;spec=svn664"
 
 >...ld/components/cipher-core-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/cipher-core.js?r=611&amp;spec=svn664"
 
 >.../build/components/cipher-core.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/core-min.js?r=611&amp;spec=svn664"
 
 >...1.2/build/components/core-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/core.js?r=611&amp;spec=svn664"
 
 >...s/3.1.2/build/components/core.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/enc-base64-min.js?r=611&amp;spec=svn664"
 
 >...ild/components/enc-base64-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/enc-base64.js?r=611&amp;spec=svn664"
 
 >...2/build/components/enc-base64.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/enc-utf16-min.js?r=611&amp;spec=svn664"
 
 >...uild/components/enc-utf16-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/enc-utf16.js?r=611&amp;spec=svn664"
 
 >....2/build/components/enc-utf16.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/evpkdf-min.js?r=611&amp;spec=svn664"
 
 >...2/build/components/evpkdf-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/evpkdf.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/components/evpkdf.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/format-hex-min.js?r=611&amp;spec=svn664"
 
 >...ild/components/format-hex-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/format-hex.js?r=611&amp;spec=svn664"
 
 >...2/build/components/format-hex.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/hmac-min.js?r=611&amp;spec=svn664"
 
 >...1.2/build/components/hmac-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/hmac.js?r=611&amp;spec=svn664"
 
 >...s/3.1.2/build/components/hmac.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/lib-typedarrays-min.js?r=611&amp;spec=svn664"
 
 >...omponents/lib-typedarrays-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/lib-typedarrays.js?r=611&amp;spec=svn664"
 
 >...ld/components/lib-typedarrays.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/md5-min.js?r=611&amp;spec=svn664"
 
 >....1.2/build/components/md5-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/md5.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/components/md5.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-cfb-min.js?r=611&amp;spec=svn664"
 
 >...build/components/mode-cfb-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-cfb.js?r=611&amp;spec=svn664"
 
 >...1.2/build/components/mode-cfb.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ctr-gladman-min.js?r=611&amp;spec=svn664"
 
 >...mponents/mode-ctr-gladman-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ctr-gladman.js?r=611&amp;spec=svn664"
 
 >...d/components/mode-ctr-gladman.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ctr-min.js?r=611&amp;spec=svn664"
 
 >...build/components/mode-ctr-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ctr.js?r=611&amp;spec=svn664"
 
 >...1.2/build/components/mode-ctr.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ecb-min.js?r=611&amp;spec=svn664"
 
 >...build/components/mode-ecb-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ecb.js?r=611&amp;spec=svn664"
 
 >...1.2/build/components/mode-ecb.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ofb-min.js?r=611&amp;spec=svn664"
 
 >...build/components/mode-ofb-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/mode-ofb.js?r=611&amp;spec=svn664"
 
 >...1.2/build/components/mode-ofb.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-ansix923-min.js?r=611&amp;spec=svn664"
 
 >...d/components/pad-ansix923-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-ansix923.js?r=611&amp;spec=svn664"
 
 >...build/components/pad-ansix923.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-iso10126-min.js?r=611&amp;spec=svn664"
 
 >...d/components/pad-iso10126-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-iso10126.js?r=611&amp;spec=svn664"
 
 >...build/components/pad-iso10126.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-iso97971-min.js?r=611&amp;spec=svn664"
 
 >...d/components/pad-iso97971-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-iso97971.js?r=611&amp;spec=svn664"
 
 >...build/components/pad-iso97971.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-nopadding-min.js?r=611&amp;spec=svn664"
 
 >.../components/pad-nopadding-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-nopadding.js?r=611&amp;spec=svn664"
 
 >...uild/components/pad-nopadding.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-zeropadding-min.js?r=611&amp;spec=svn664"
 
 >...omponents/pad-zeropadding-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pad-zeropadding.js?r=611&amp;spec=svn664"
 
 >...ld/components/pad-zeropadding.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pbkdf2-min.js?r=611&amp;spec=svn664"
 
 >...2/build/components/pbkdf2-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/pbkdf2.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/components/pbkdf2.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/rabbit-legacy-min.js?r=611&amp;spec=svn664"
 
 >.../components/rabbit-legacy-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/rabbit-legacy.js?r=611&amp;spec=svn664"
 
 >...uild/components/rabbit-legacy.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/rabbit-min.js?r=611&amp;spec=svn664"
 
 >...2/build/components/rabbit-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/rabbit.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/components/rabbit.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/rc4-min.js?r=611&amp;spec=svn664"
 
 >....1.2/build/components/rc4-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/rc4.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/components/rc4.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/ripemd160-min.js?r=611&amp;spec=svn664"
 
 >...uild/components/ripemd160-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/ripemd160.js?r=611&amp;spec=svn664"
 
 >....2/build/components/ripemd160.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha1-min.js?r=611&amp;spec=svn664"
 
 >...1.2/build/components/sha1-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha1.js?r=611&amp;spec=svn664"
 
 >...s/3.1.2/build/components/sha1.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha224-min.js?r=611&amp;spec=svn664"
 
 >...2/build/components/sha224-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha224.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/components/sha224.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha256-min.js?r=611&amp;spec=svn664"
 
 >...2/build/components/sha256-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha256.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/components/sha256.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha3-min.js?r=611&amp;spec=svn664"
 
 >...1.2/build/components/sha3-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha3.js?r=611&amp;spec=svn664"
 
 >...s/3.1.2/build/components/sha3.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha384-min.js?r=611&amp;spec=svn664"
 
 >...2/build/components/sha384-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha384.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/components/sha384.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha512-min.js?r=611&amp;spec=svn664"
 
 >...2/build/components/sha512-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/sha512.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/components/sha512.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/tripledes-min.js?r=611&amp;spec=svn664"
 
 >...uild/components/tripledes-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/tripledes.js?r=611&amp;spec=svn664"
 
 >....2/build/components/tripledes.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/x64-core-min.js?r=611&amp;spec=svn664"
 
 >...build/components/x64-core-min.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/components/x64-core.js?r=611&amp;spec=svn664"
 
 >...1.2/build/components/x64-core.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/aes.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/aes.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-md5.js?r=611&amp;spec=svn664"
 
 >.../3.1.2/build/rollups/hmac-md5.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-ripemd160.js?r=611&amp;spec=svn664"
 
 >.../build/rollups/hmac-ripemd160.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha1.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/rollups/hmac-sha1.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha224.js?r=611&amp;spec=svn664"
 
 >...1.2/build/rollups/hmac-sha224.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha256.js?r=611&amp;spec=svn664"
 
 >...1.2/build/rollups/hmac-sha256.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha3.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/rollups/hmac-sha3.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha384.js?r=611&amp;spec=svn664"
 
 >...1.2/build/rollups/hmac-sha384.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/hmac-sha512.js?r=611&amp;spec=svn664"
 
 >...1.2/build/rollups/hmac-sha512.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/md5.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/md5.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/pbkdf2.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/pbkdf2.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/rabbit-legacy.js?r=611&amp;spec=svn664"
 
 >...2/build/rollups/rabbit-legacy.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/rabbit.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/rabbit.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/rc4.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/rc4.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/ripemd160.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/rollups/ripemd160.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha1.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/sha1.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha224.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/sha224.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha256.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/sha256.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha3.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/sha3.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha384.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/sha384.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/sha512.js?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/build/rollups/sha512.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/build/rollups/tripledes.js?r=611&amp;spec=svn664"
 
 >...3.1.2/build/rollups/tripledes.js</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/builder/build.yml?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/builder/build.yml</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/builder/copyright.php?r=611&amp;spec=svn664"
 
 >/tags/3.1.2/builder/copyright.php</option>
 
 <option value="/p/crypto-js/source/browse/tags/3.1.2/test/lib-typedarrays-test.js?r=611&amp;spec=svn664"
 
 >...1.2/test/lib-typedarrays-test.js</option>
 
 </select>
 </td></tr></table>
 
 
 



 <div style="white-space:nowrap">
 
 <a href="https://www.google.com/accounts/ServiceLogin?service=code&amp;ltmpl=phosting&amp;continue=https%3A%2F%2Fcode.google.com%2Fp%2Fcrypto-js%2Fsource%2Fbrowse%2Ftags%2F3.1.2%2Fsrc%2Fpbkdf2.js&amp;followup=https%3A%2F%2Fcode.google.com%2Fp%2Fcrypto-js%2Fsource%2Fbrowse%2Ftags%2F3.1.2%2Fsrc%2Fpbkdf2.js"
 >Sign in</a> to write a code review</div>


 
 </div>
 
 
 </div>
 <div class="round1"></div>
 <div class="round2"></div>
 <div class="round4"></div>
 </div>
 <div class="pmeta_bubble_bg" style="border:1px solid white">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <div id="older_bubble">
 <p>Older revisions</p>
 
 
 <div class="closed" style="margin-bottom:3px;" >
 <a class="ifClosed" onclick="return _toggleHidden(this)"><img src="https://ssl.gstatic.com/codesite/ph/images/plus.gif" ></a>
 <a class="ifOpened" onclick="return _toggleHidden(this)"><img src="https://ssl.gstatic.com/codesite/ph/images/minus.gif" ></a>
 <a href="/p/crypto-js/source/detail?spec=svn664&r=554">r554</a>
 by Jeff.Mott.OR
 on Nov 2, 2012
 &nbsp; <a href="/p/crypto-js/source/diff?spec=svn664&r=554&amp;format=side&amp;path=/branches/3.x/src/pbkdf2.js&amp;old_path=/branches/3.1.x/src/pbkdf2.js&amp;old=548">Diff</a>
 <br>
 <pre class="ifOpened">The 3.1.x code line will continue to
be the 3.x code line. What was
formerly the 3.x code line, which
includes refactoring, will be the 4.x
code line.</pre>
 </div>
 
 
 
 <div class="closed" style="margin-bottom:3px;" >
 <a class="ifClosed" onclick="return _toggleHidden(this)"><img src="https://ssl.gstatic.com/codesite/ph/images/plus.gif" ></a>
 <a class="ifOpened" onclick="return _toggleHidden(this)"><img src="https://ssl.gstatic.com/codesite/ph/images/minus.gif" ></a>
 <a href="/p/crypto-js/source/detail?spec=svn664&r=548">r548</a>
 by Jeff.Mott.OR
 on Oct 3, 2012
 &nbsp; <a href="/p/crypto-js/source/diff?spec=svn664&r=548&amp;format=side&amp;path=/branches/3.1.x/src/pbkdf2.js&amp;old_path=/branches/3.x/src/pbkdf2.js&amp;old=439">Diff</a>
 <br>
 <pre class="ifOpened">Created 3.1.x branch from 3.0.2 line.</pre>
 </div>
 
 
 
 <div class="closed" style="margin-bottom:3px;" >
 <a class="ifClosed" onclick="return _toggleHidden(this)"><img src="https://ssl.gstatic.com/codesite/ph/images/plus.gif" ></a>
 <a class="ifOpened" onclick="return _toggleHidden(this)"><img src="https://ssl.gstatic.com/codesite/ph/images/minus.gif" ></a>
 <a href="/p/crypto-js/source/detail?spec=svn664&r=439">r439</a>
 by Jeff.Mott.OR
 on Mar 19, 2012
 &nbsp; <a href="/p/crypto-js/source/diff?spec=svn664&r=439&amp;format=side&amp;path=/branches/3.x/src/pbkdf2.js&amp;old_path=/branches/3.x/src/pbkdf2.js&amp;old=436">Diff</a>
 <br>
 <pre class="ifOpened">Minor refactoring, performance
improvements, and updated tests and
profiles.</pre>
 </div>
 
 
 <a href="/p/crypto-js/source/list?path=/tags/3.1.2/src/pbkdf2.js&start=611">All revisions of this file</a>
 </div>
 </div>
 <div class="round1"></div>
 <div class="round2"></div>
 <div class="round4"></div>
 </div>
 
 <div class="pmeta_bubble_bg" style="border:1px solid white">
 <div class="round4"></div>
 <div class="round2"></div>
 <div class="round1"></div>
 <div class="box-inner">
 <div id="fileinfo_bubble">
 <p>File info</p>
 
 <div>Size: 4125 bytes,
 125 lines</div>
 
 <div><a href="//crypto-js.googlecode.com/svn/tags/3.1.2/src/pbkdf2.js">View raw file</a></div>
 </div>
 
 </div>
 <div class="round1"></div>
 <div class="round2"></div>
 <div class="round4"></div>
 </div>
 </div>
 </div>


</div>

</div>
</div>

<script src="https://ssl.gstatic.com/codesite/ph/13714586478408612229/js/prettify/prettify.js"></script>
<script type="text/javascript">prettyPrint();</script>


<script src="https://ssl.gstatic.com/codesite/ph/13714586478408612229/js/source_file_scripts.js"></script>

 <script type="text/javascript" src="https://ssl.gstatic.com/codesite/ph/13714586478408612229/js/kibbles.js"></script>
 <script type="text/javascript">
 var lastStop = null;
 var initialized = false;
 
 function updateCursor(next, prev) {
 if (prev && prev.element) {
 prev.element.className = 'cursor_stop cursor_hidden';
 }
 if (next && next.element) {
 next.element.className = 'cursor_stop cursor';
 lastStop = next.index;
 }
 }
 
 function pubRevealed(data) {
 updateCursorForCell(data.cellId, 'cursor_stop cursor_hidden');
 if (initialized) {
 reloadCursors();
 }
 }
 
 function draftRevealed(data) {
 updateCursorForCell(data.cellId, 'cursor_stop cursor_hidden');
 if (initialized) {
 reloadCursors();
 }
 }
 
 function draftDestroyed(data) {
 updateCursorForCell(data.cellId, 'nocursor');
 if (initialized) {
 reloadCursors();
 }
 }
 function reloadCursors() {
 kibbles.skipper.reset();
 loadCursors();
 if (lastStop != null) {
 kibbles.skipper.setCurrentStop(lastStop);
 }
 }
 // possibly the simplest way to insert any newly added comments
 // is to update the class of the corresponding cursor row,
 // then refresh the entire list of rows.
 function updateCursorForCell(cellId, className) {
 var cell = document.getElementById(cellId);
 // we have to go two rows back to find the cursor location
 var row = getPreviousElement(cell.parentNode);
 row.className = className;
 }
 // returns the previous element, ignores text nodes.
 function getPreviousElement(e) {
 var element = e.previousSibling;
 if (element.nodeType == 3) {
 element = element.previousSibling;
 }
 if (element && element.tagName) {
 return element;
 }
 }
 function loadCursors() {
 // register our elements with skipper
 var elements = CR_getElements('*', 'cursor_stop');
 var len = elements.length;
 for (var i = 0; i < len; i++) {
 var element = elements[i]; 
 element.className = 'cursor_stop cursor_hidden';
 kibbles.skipper.append(element);
 }
 }
 function toggleComments() {
 CR_toggleCommentDisplay();
 reloadCursors();
 }
 function keysOnLoadHandler() {
 // setup skipper
 kibbles.skipper.addStopListener(
 kibbles.skipper.LISTENER_TYPE.PRE, updateCursor);
 // Set the 'offset' option to return the middle of the client area
 // an option can be a static value, or a callback
 kibbles.skipper.setOption('padding_top', 50);
 // Set the 'offset' option to return the middle of the client area
 // an option can be a static value, or a callback
 kibbles.skipper.setOption('padding_bottom', 100);
 // Register our keys
 kibbles.skipper.addFwdKey("n");
 kibbles.skipper.addRevKey("p");
 kibbles.keys.addKeyPressListener(
 'u', function() { window.location = detail_url; });
 kibbles.keys.addKeyPressListener(
 'r', function() { window.location = detail_url + '#publish'; });
 
 kibbles.keys.addKeyPressListener('j', gotoNextPage);
 kibbles.keys.addKeyPressListener('k', gotoPreviousPage);
 
 
 }
 </script>
<script src="https://ssl.gstatic.com/codesite/ph/13714586478408612229/js/code_review_scripts.js"></script>
<script type="text/javascript">
 function showPublishInstructions() {
 var element = document.getElementById('review_instr');
 if (element) {
 element.className = 'opened';
 }
 }
 var codereviews;
 function revsOnLoadHandler() {
 // register our source container with the commenting code
 var paths = {'svn664': '/tags/3.1.2/src/pbkdf2.js'}
 codereviews = CR_controller.setup(
 {"assetHostPath":"https://ssl.gstatic.com/codesite/ph","relativeBaseUrl":"","projectName":"crypto-js","assetVersionPath":"https://ssl.gstatic.com/codesite/ph/13714586478408612229","profileUrl":null,"domainName":null,"loggedInUserEmail":null,"token":null,"projectHomeUrl":"/p/crypto-js"}, '', 'svn664', paths,
 CR_BrowseIntegrationFactory);
 
 codereviews.registerActivityListener(CR_ActivityType.REVEAL_DRAFT_PLATE, showPublishInstructions);
 
 codereviews.registerActivityListener(CR_ActivityType.REVEAL_PUB_PLATE, pubRevealed);
 codereviews.registerActivityListener(CR_ActivityType.REVEAL_DRAFT_PLATE, draftRevealed);
 codereviews.registerActivityListener(CR_ActivityType.DISCARD_DRAFT_COMMENT, draftDestroyed);
 
 
 
 
 
 
 
 var initialized = true;
 reloadCursors();
 }
 window.onload = function() {keysOnLoadHandler(); revsOnLoadHandler();};

</script>
<script type="text/javascript" src="https://ssl.gstatic.com/codesite/ph/13714586478408612229/js/dit_scripts.js"></script>

 
 
 
 <script type="text/javascript" src="https://ssl.gstatic.com/codesite/ph/13714586478408612229/js/ph_core.js"></script>
 
 
 
 
</div> 

<div id="footer" dir="ltr">
 <div class="text">
 <a href="/projecthosting/terms.html">Terms</a> -
 <a href="http://www.google.com/privacy.html">Privacy</a> -
 <a href="/p/support/">Project Hosting Help</a>
 </div>
</div>
 <div class="hostedBy" style="margin-top: -20px;">
 <span style="vertical-align: top;">Powered by <a href="http://code.google.com/projecthosting/">Google Project Hosting</a></span>
 </div>

 
 


 
 </body>
</html>

