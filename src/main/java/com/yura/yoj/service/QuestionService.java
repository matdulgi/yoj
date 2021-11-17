package com.yura.yoj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yura.yoj.dao.QuestionDao;
import com.yura.yoj.dto.DtoFunction;
import com.yura.yoj.dto.QuestionDto;
import com.yura.yoj.dto.StatusDto;
import com.yura.yoj.runtimeCommand.SubmitCommand;


public class QuestionService {
	QuestionDao questionDao = QuestionDao.getInstance();

	public QuestionService() {
	}

	public void addQuestion() {

	}

	public void delQuestion() {

	}

	public void modifyQuestion() {

	}

	public String questionList(HttpServletRequest req) {
		QuestionDao questionDao = QuestionDao.getInstance();

		List<QuestionDto> questionList = questionDao.getQuestionList();
		req.setAttribute("questionList", questionList);
		return "question/questionList";
	}


	Entry<String, Object> getEntry(String key, Object value) {
		Entry<String, Object> selectConditionEntry = new Entry<String, Object>() {

			@Override
			public Object setValue(Object value) {
				return null;
			}

			@Override
			public Object getValue() {
				return value;
			}

			@Override
			public String getKey() {
				return key;
			}
		};
		return selectConditionEntry;
	}

	
	
	public String viewQuestion(HttpServletRequest req) {
		int questionNum = Integer.parseInt(req.getParameter("questionNum"));
//		QuestionDto questionDto = new QuestionDto();
//		questionDao.selectOne("questionView", "questionNum", questionNum, questionDto);
		QuestionDto questionDto = questionDao.viewQuestion(questionNum);
		req.setAttribute("questionDto", questionDto);

		return "/question/question.jsp";
	}
	
	
	public String submit(HttpServletRequest req) {
		String source = req.getParameter("source");
		String questionNum = req.getParameter("questionNum");
		String loginId = (String) req.getSession().getAttribute("loginId");
		String lang = req.getParameter("lang");
		SubmitCommand submitCommand = new SubmitCommand();

		// 일단 푼 문제는 정답이든 아니든 db에 저장
		// dto를 새로 만들 것인지 status dto를 사용할 것인지...
		// 일단 statusDto사용.
		// 접때 고민하던 dto 기능이 필요하다....
		// setDto시 set된 프로퍼티의 목록을 set에 저장
		StatusDto statusDto = new StatusDto();
		Set<String> settedPropertySet = DtoFunction.setPropertyFromRequest(req, statusDto);
		req.setAttribute("statysDto", statusDto);
		req.setAttribute("settedPropertySet", settedPropertySet);

		String filePath = "userSource/" + loginId + "/" + questionNum + "/" + lang + "/";
		String fileRealPath = req.getSession().getServletContext().getRealPath(filePath);
		String fileName = "testFile"; // 임시. 나중에 dao에서 읽어와서 숫자 부여
		System.out.println("filePath : " + fileRealPath + fileName);

		submitCommand.writeFile(fileRealPath, fileName, source);

		String compileCmd = "gcc " + fileRealPath + fileName + ".c -o " + fileRealPath + fileName + ".exe";
		String executeCmd = fileRealPath + fileName + ".exe > " + fileRealPath + fileName + ".txt";
//		String removeCmd = "del " + fileRealPath+fileName + ".exe " + fileRealPath+fileName + ".txt ";
		// 우선 폴더안의 내용 다 삭제하자...
		String removeCmd = "del /s /q " + fileRealPath + "*";

		// 문제....... 런타임 객체를 돌려쓸것인가 한번에 계속 쓸것인가.ㅣ......... 우선 돌려써보자
		Runtime run = Runtime.getRuntime();
		// 우선 명령들은 윈도우 기준으로 하고 추후에 리눅스랑 분기를 나눈다
		// 컴파일 우선
		submitCommand.execCmd(compileCmd, run);
		// 실행2 실행파일의 결과를 파일로 출력
		submitCommand.execCmd(executeCmd, run);
		// 결과 받아오기
		String userResult = submitCommand.readFile(fileRealPath + fileName + ".txt");
		String correctAnswer = "";
		// 파일 삭제
		submitCommand.execCmd(removeCmd, run);

		// status에서 조회할 때 참고할 때 필터 정보 : memberID, questionNum
		// db를 조회할 때의 조건절을 위해 List<Entry>를 만들어 전달한다
		// 왜 List인고 하니 PreparedStatement에서 sql 만들때랑 값 셋팅할때의 순서가 같아야한다
		// 근데 List에 Entry를 넣으려면 ..... Entry를 만들어야 하는데 그 메소드를 어딘가에 만들고싶다
		// 어디넣을까.... 1. Handler 2. DtoFunction 3. CommonDao
		List<Entry<String, Object>> conditionList = new ArrayList<Entry<String, Object>>();
		Entry<String, Object> loginIdEntry = getEntry("loginId", loginId);
		conditionList.add(loginIdEntry);
		Entry<String, Object> questionNumEntry = getEntry("loginId", loginId);
		conditionList.add(questionNumEntry);
		req.setAttribute("conditionList", conditionList);

//		return "/status/status.jsp";
		return "/status/status.do";
	}


}
