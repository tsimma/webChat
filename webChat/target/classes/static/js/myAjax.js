setInterval(function() {
	  $('#dynamicDiv').load('/chat/content-to-refresh #dynamicDiv'); 
	}, 1000);

/*	async function refreshContent() {
  const response = await fetch('/chat/content-to-refresh');
  const html = await response.text();
  document.getElementById('dynamicDiv').innerHTML = html; 
}

setInterval(refreshContent, 1000); // Задаем обновление каждые 5 секунд*/