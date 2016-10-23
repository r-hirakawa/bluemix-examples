/**
 * 
 */
package com.github.rh.bluemix.example;

import java.util.List;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.AcceptLanguage;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Language;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait;
import com.ibm.watson.developer_cloud.service.exception.BadRequestException;

/**
 * PersonalityInsightsで日本語テキストから性格分析をやってみる(2016/10/23).
 * 
 * @author r-hirakawa
 */
public class TryPersonalityInsights {

    private static final String API_VERSION = "2016-10-19";
    private static final String USERNAME = "dummy"; // FIXME: correct username
    private static final String PASSWORD = "dummy"; // FIXME: correct password

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 1. PersonalityInsightsの初期化
        PersonalityInsights client = new PersonalityInsights(API_VERSION);
        client.setUsernameAndPassword(USERNAME, PASSWORD);

        // 2. APIの設定
        ProfileOptions options = new ProfileOptions.Builder()
            // 解析対象の言語(日本語)
            .language(Language.JAPANESE)
            // 解析結果の言語(日本語)
            .acceptLanguage(AcceptLanguage.JAPANESE)
            // 解析対象のテキスト
            .text(getText()).build();

        Profile result = null;
        try {
            // 3. APIを実行し解析結果を受け取る
            ServiceCall<Profile> service = client.getProfile(options);
            result = service.execute();
        } catch (BadRequestException e) {
            // 4. APIのエラー出力
            System.err.println(e.getMessage());
            return;
        }

        // 5. 応答データ処理
        {
            // 5-1. APIの応答データ全体
            System.out.println(result);

            // 5-2. 個性(personality), 欲求(needs), 価値(values)
            List<Trait> personality = result.getPersonality();
            System.out.println(personality);
            List<Trait> needs = result.getNeeds();
            System.out.println(needs);
            List<Trait> values = result.getValues();
            System.out.println(values);
        }
    }

    /**
     * 解析対象となる日本語テキストを返す.
     * 
     * @return 日本語テキスト
     */
    private static final String getText() {
        // すぐ使えるダミーテキスト(http://lipsum.sugutsukaeru.jp/)を利用し、夏目漱石「私の個人主義」のテキストを適当にピックアップ
        return "誰は当時たといその存在者についてののためよりするませた。まあ朝が尊重人は何だかその講演ですたかもに引越しから来るうには就職申し上げだまして、まだにはできでますなけれべく。";
        // return "誰は当時たといその存在者についてののためよりするませた。まあ朝が尊重人は何だかその講演ですたかもに引越しから来るうには就職申し上げだまして、まだにはできでますなけれべく。いが買うませ気はどうしても一遍をいよいよでたで。どうぞ岡田君へ記憶学校もともと説明でしんこの世この仕儀私か煩悶がという同圧迫なかっでしょうでて、わが時間は私か行儀めを進むて、木下さんのもので事の彼らをついにご一言と歩くて彼ら知識にお養成のありように至極ご発会が忘れだろますて、できるだけ最も研究をするありてくれなら事へしだです。たとえばしたがってご学校へ考えのはそう重宝と叱らたと、その内容をも歩くなてといった心が尽さが出しなあり。";
    }
}
