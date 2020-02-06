from slacker import Slacker
import datetime

def get_week_number(dt):
    """
        datetime型を受け取って、週番号を返す
    """
    day = dt.day # 日付の取得
    week = 0
    while day > 0:
        week += 1 
        day -= 7
    return week

def post_regular(slack):
    """
        指定した週番号の曜日だった場合slackに送信

        weekday number:
            0 = Monday, 1 = Tuseday, 2 = Wednesday, 3 = Thursday, 4 = Friday, 5 = Saturday, 6 = Sunday

        tomorrow.weekend() -> 曜日を取得（int型）
        g = ごみの種類
    """
    today = datetime.datetime.now() # 今日の日付を取得
    TARGET_WEEKNUMBER = (1,3)

    # 現在の日付に1日だけプラスする = 明日の日付の取得
    tomorrow = today + datetime.timedelta(days=1)

    # 明日の曜日が月曜日の時
    if tomorrow.weekday() == 0:
        g = ''

    # 明日の曜日が火曜日の時
    if tomorrow.weekday() == 1:
        g = ''

    # 明日の曜日が水曜日の時
    if tomorrow.weekday() == 2:
        # 第１・３週の水曜日
        if get_week_number(tomorrow) in TARGET_WEEKNUMBER:
            g = ''
        # 第2・4週の水曜日
        if get_week_number(tomorrow) not in TARGET_WEEKNUMBER:
            g = ''
        
    # 明日の曜日が木曜日の時
    if tomorrow.weekday() == 3:
        # 第１・３週の木曜日
        if get_week_number(tomorrow) in TARGET_WEEKNUMBER:
            g = ''
        # 第2・4・５週の木曜日
        if get_week_number(tomorrow) not in TARGET_WEEKNUMBER:
            g = ''

    # 明日の曜日が金曜日の時
    if tomorrow.weekday() == 4:
        g = ''

    slack.chat.post_message('your channel name','明日は'+s+'の日です'+'\nお忘れずに！')    

def main():
    slack_token = "your slack api token"
    slack = Slacker(slack_token)
    post_regular(slack)

if __name__ == '__main__':
    main()