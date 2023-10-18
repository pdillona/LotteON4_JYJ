/**
 * 이메일 인증
 */
$(function(){
	// 이메일 입력방식 선택
	$('#selectEmail').change(function() {
		var selectedOption = $("#selectEmail option:selected").val();
		console.log("selectedOption : "+selectedOption);

		if (selectedOption === '1') { // 직접입력일 경우
			$("#lo_email2").attr("placeholder", '도메인 입력');
			$("#lo_email2").prop("disabled", false).val('');
		} else { // 직접입력이 아닐 경우
			$("#lo_email2").attr("placeholder", selectedOption);
			$("#lo_email2").prop("disabled", true).val(selectedOption);
		}
	});

	let preventDoubleClick = false;
	
	$('#btnEmailCode').click(function(){
		
		const division = $('input[name=division]').val();
		const name = $('input[name=name]').val();
		const uid = $('input[name=uid]').val();
		const email = $('input[name=email]').val();
		
		const jsonData = {
									"division" : division,
									"name": name, 
									"uid": uid, 
									"email":email
									};
		
		if(preventDoubleClick){
			return;
		}
		preventDoubleClick = true;
		$('.msgEmail').css('color', 'black').text('잠시만 기다리세요...');
		$('.resultEmailForId').css('color', 'black').text('잠시만 기다리세요...');
		$('.resultEmailForPass').css('color', 'black').text('잠시만 기다리세요...');
		
		setTimeout(function(){
			$.ajax({
				url:'/LotteON/member/email/authEmail',
				type:'GET',
				data: JSON.stringify(jsonData),
				contentType : "application/json; charset=UTF-8",
				dataType:'json',
				success:function(data){
					if(data.result > 0) {
						$('.msgEmail').css('color', 'red').text('이미 사용중인 이메일입니다.');
						isEmaileOk =false;
					}else {
						if(data.status > 0){
							$('.msgEmail').css('color', 'green').text('이메일을 확인 후 인증코드를 입력하세요.');
							$('.auth').show();
							$('input[name=email]').attr('readonly', true);
						}else{
							$('.msgEmail').css('color', 'red').text('이메일 인증코드 전송이 실패했습니다. 잠시후 다시 시도하십시오.');
						}
					}
					preventDoubleClick = false;						
				},
				error : function(xhr, stat, err) {

					alert("error");

					console.log(err);
				}

			});
		}, 1000);
	});
	$('#btnEmailAuth').click(function(){
		const code = $('input[name=auth]').val();
		const jsonData = {"code" : code};
		$.ajax({
			url:'/Jboard2/user/authEmail.do',
			type:'POST',
			data:jsonData,
			dataType:'json',
			success: function(data){
				if(data.result > 0) {
					$('.resultEmail').css('color', 'green').text('이메일 인증이 완료 되었습니다.');
					$('.resultEmailForId').css('color', 'green').text('이메일 인증이 완료 되었습니다.');
					$('.resultEmailForPass').css('color', 'green').text('이메일 인증이 완료 되었습니다.');
					isEmailOk = true;
				}else{
					$('.resultEmail').css('color', 'red').text('이메일 인증에 실패했습니다.');
					$('.resultEmailForId').css('color', 'red').text('이메일 인증에 실패했습니다.');
					$('.resultEmailForPass').css('color', 'red').text('이메일 인증에 실패했습니다.');
					isEmailOk = false;
				}
			}
		});
	});
});