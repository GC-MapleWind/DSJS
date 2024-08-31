document.addEventListener("DOMContentLoaded", function () {
    const progressMessage = document.getElementById("progressMessage");

    // 폼 제출 이벤트 리스너
    document.getElementById("uploadForm").addEventListener("submit", async function (event) {
        event.preventDefault(); // 폼의 기본 제출 동작 방지

        const form = event.target;
        const formData = new FormData(form);
        const dateInput = document.getElementById("dateInput").value;

        // 날짜 형식 검사 (YYYY-MM-DD)
        const datePattern = /^\d{4}-\d{2}-\d{2}$/;
        if (!dateInput.match(datePattern)) {
            alert("날짜 형식이 올바르지 않습니다.");
            return;
        }

        // 진행 중 메시지 표시
        progressMessage.style.display = 'block';

        try {
            const response = await fetch("/character/uploadAndFetch", {
                method: "POST",
                body: formData
            });

            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                const result = await response.json();
                alert(result.message);
            } else {
                const resultText = await response.text();
                alert(resultText);  // JSON이 아닌 경우 텍스트로 처리
            }
        } catch (error) {
            alert(error.message);
        } finally {
            // 진행 중 메시지 숨기기
            progressMessage.style.display = 'none';
        }
    });

    // 버튼 클릭 이벤트 리스너
    document.getElementById('exportBtn').addEventListener('click', function () {
        // 진행 중 메시지 표시
        progressMessage.style.display = 'block';

        fetch('/character/export-characters/excel', {
            method: 'GET',
        })
            .then(response => {
                if (!response.ok) {
                    alert(response.statusText);
                }
                return response.blob();
            })
            .then(blob => {
                const url = window.URL.createObjectURL(new Blob([blob]));
                const a = document.createElement('a');
                a.style.display = 'none';
                a.href = url;
                a.download = '단생조사.xlsx';
                document.body.appendChild(a);
                a.click();
                window.URL.revokeObjectURL(url);
            })
            .catch(error => {
                alert(error.message);
            })
            .finally(() => {
                // 진행 중 메시지 숨기기
                progressMessage.style.display = 'none';
            });
    });
});