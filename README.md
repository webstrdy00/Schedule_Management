schedule_api
﻿

POST
일정 작성
http://localhost:8080/api/schedules
﻿

Body
raw (json)
json
{
    "work" : "영양제 먹기",
    "manager" : "donghwi",
    "password" : 12345
}
GET
선택 일정 조회
http://localhost:8080/api/schedules/{{id}}
﻿

GET
일정 목록 조회
http://localhost:8080/api/schedules
﻿

PUT
선택한 일정 수정
http://localhost:8080/api/schedules/{{id}}
﻿

DELETE
선택한 일정 삭제
http://localhost:8080/api/schedules/{{id}}
﻿

