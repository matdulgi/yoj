package com.yura.yoj.runtimeCommand;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SubmitCommand {

	public String getWinCmd(String command) {
//		String convertedCmd = "cmd.exe /c " + command;
//		return convertedCmd;
		return "cmd.exe /c " + command;
	}

	public String getLinuxCommand() {
		return "";
	}

	// 지정 파일 경로로 소스 출력
	public void writeFile(String filePath, String fileName, String source) {
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File file = new File(filePath + fileName + ".c");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(source.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// 아오......
	// 현재 컴파일이 채 완료되기도 전에 FileInputStream이 실행되는 문제
	// 자바에서는 출력 동작이 끝났지만 컴퓨터에서는 채 컴파일이 끝나지 않은 문제
	// 해당 문제는 컴파일 타임을 구하기 위해서라도 반드시 해결해야한다
	// 혹시나해서 2초 sleep해보니까 예외는 안뜨는데 여전히 실행파일은 안뜬다
	// 즉, fis 이전에 cmd에서 txt로 내보내는데도 문제가 있는듯
	// 실행파일이 생성이 됐는데 결과(cmd2)가 나와있지 않다-> 컴파일이 끝나기도 전에 cmd2가 실행이 되었다

	public void execCmd(String cmd, Runtime run) {
		long startTime = System.currentTimeMillis();
		String winCmd = getWinCmd(cmd);
		try {
			System.out.println("코만도 : " + winCmd);
			Process process = run.exec(winCmd);
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		long compileTime = System.currentTimeMillis() - startTime;
		System.out.println("작업 소요시간 :" + compileTime);
	}

	// 파일 받아오기
	// 입출력 스트림을 향상된??? 그걸로하면 해제되는데 지연시간이 걸려서 다음 작업에 문제가 될 수도 있다
	public String readFile(String filePath) {
		int read;
		String userResult = "";
		StringBuffer stringBuffer = new StringBuffer();
		FileInputStream fileInputStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			fileInputStream = new FileInputStream(filePath);
			bufferedInputStream = new BufferedInputStream(fileInputStream);
//			while( (read = fileInputStream.read()) != -1) {
			while ((read = bufferedInputStream.read()) != -1) {
				stringBuffer.append((char) read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedInputStream.close();
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		userResult = stringBuffer.toString();
		System.out.println("결과는~? : " + userResult);
		return userResult;
	}
}
