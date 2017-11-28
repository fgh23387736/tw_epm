<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<?php include $_SERVER['DOCUMENT_ROOT'].'/sanhe/webContent/end/link.php';?>
<style type="text/css">
    .addDataMain{
        width: 800px;
        margin: 30px auto;
    }
</style>
<div class="addDataMain">
    <form action="#" method="post" id="form">
        <table class="table table-bordered">
            <tr>
                <td>手机号:<span style='color:red;'>*</span></td>
                <td>
                    <input type="text" id="Phone" placeholder="手机号" name="Phone" class="form-control input-lg">
                </td>
            </tr>
            <tr>
                <td>密码:<span style='color:red;'>*</span></td>
                <td>
                    <input type="password" id="Password" placeholder="密码" name="Password" class="form-control input-lg">
                </td>
            </tr>
            <tr>
                <td>重复密码:<span style='color:red;'>*</span></td>
                <td>
                    <input type="password" id="RepeatPassword" placeholder="重复密码" name="RepeatPassword" class="form-control input-lg">
                </td>
            </tr>
            <tr>
                <td>级别:<span style='color:red;'>*</span></td>
                <td>
                    <input type="number" id="Level" placeholder="级别" name="Level" class="form-control input-lg" min="<?php echo $_SESSION['level']+1?>">
                </td>
            </tr>
        </table>
    </form>
    <div class="btn btn-primary btn-lg" onclick="submitData()">提交</div>
</div>
<script type="text/javascript">
    <?php 
        $id='';
        if(isset($_GET['id'])){
            if(is_numeric($_GET['id'])){
                $id=$_GET['id'];
            }
        }
    ?>
    var id="<?php echo $id;?>";
</script>
<script type="text/javascript" src="/sanhe/static/js/jquery.min.js"></script>
<script type="text/javascript" src="/sanhe/static/js/layer/layer.js"></script>
<script type="text/javascript" src='/sanhe/static/js/md5.js'></script>
<script type="text/javascript" src="addUser.js"></script>
