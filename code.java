import okhttp3.*;

import java.io.IOException;
import java.util.Scanner;

public class healthTrack {
    private static final String API_KEY = "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";
    private static final String MODEL_ID = "gpt-3.5-turbo";

    public static void main(String[] args) {
        // 注册
        register();

        // 登录
        login();

        // 录入身高体重
        enterHeightWeight();

        // 进行问答
        startChat();
    }

    private static void register() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 注册 ===");
        System.out.print("请输入用户名：");
        String username = scanner.nextLine();

        System.out.print("请输入密码：");
        String password = scanner.nextLine();

        // 在这里调用注册API，将用户名和密码发送到服务器进行注册
        // ...

        System.out.println("注册成功！");
        System.out.println();
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 登录 ===");
        System.out.print("请输入用户名：");
        String username = scanner.nextLine();

        System.out.print("请输入密码：");
        String password = scanner.nextLine();

        // 在这里调用登录API，将用户名和密码发送到服务器进行验证
        // 如果验证成功，返回登录凭证（如token）用于后续请求
        String token = "TOKEN";

        System.out.println("登录成功！Token: " + token);
        System.out.println();
    }

    private static void enterHeightWeight() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 录入身高体重 ===");
        System.out.print("请输入身高（cm）：");
        double height = scanner.nextDouble();

        System.out.print("请输入体重（kg）：");
        double weight = scanner.nextDouble();

        // 在这里将身高体重发送到服务器进行保存
        // ...

        System.out.println("身高体重录入成功！");
        System.out.println();
    }

    private static void startChat() {
        Scanner scanner = new Scanner(System.in);
        String message;

        System.out.println("=== 开始聊天 ===");
        System.out.println("输入'退出'退出聊天");

        // 聊天循环
        do {
            System.out.print("用户输入：");
            message = scanner.nextLine();

            if (!message.equals("退出")) {
                // 将用户输入的消息发送到OpenAI Chat API进行问答
                String response = openAIChatAPI(message);
                System.out.println("AI回答：" + response);
            }
        } while (!message.equals("退出"));

        System.out.println("聊天结束！");
        System.out.println();
    }

    private static String openAIChatAPI(String message) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/json");

        String prompt = "User: " + message + "\n\nAI:";

        String jsonBody = "{\"prompt\":\"" + prompt + "\",\"max_tokens\":100}";

        RequestBody body = RequestBody.create(mediaType, jsonBody);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + "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")
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            return parseAIResponse(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String parseAIResponse(String responseBody) {
        int startIndex = responseBody.indexOf("\"text\":\"") + 8;
        int endIndex = responseBody.lastIndexOf("\"");
        return responseBody.substring(startIndex, endIndex);
    }
}
