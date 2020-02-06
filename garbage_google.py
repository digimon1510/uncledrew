from __future__ import print_function
import datetime
import pickle
import os.path
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request

# If modifying these scopes, delete the file token.pickle.
SCOPES = ['https://www.googleapis.com/auth/calendar']

class garbage_schedule:
    def get_week_number(self,dt):
        """
        datetime型を受け取って、週番号を返す
        """
        day = dt.day # 日付の取得
        week = 0
        while day > 0:
            week += 1 
            day -= 7
        return week

    def get_garbage_type(self,date):
        """
        任意の日付から、ごみの種類を返す
        引数 date：datetime型の任意の日付
        戻り値 garbage：ごみの種類
        week:
            0 = Monday, 1 = Tuseday, 2 = Wednesday, 3 = Thursday, 4 = Friday, 5 = Saturday, 6 = Sunday
        """
        TARGET_WEEKNUMBER = (1,3)

        # 月曜日の時
        if date.weekday() == 0:
            garbage = ''
    
        # 火曜日の時
        elif date.weekday() == 1:
            garbage = ''
    
        # 水曜日の時
        elif date.weekday() == 2:
            # 第１・３週の水曜日
            if self.get_week_number(date) in TARGET_WEEKNUMBER:
                garbage = ''
            # それ以外の水曜日
            else :
                garbage = ''
            
        # 木曜日の時
        elif date.weekday() == 3:
            # 第１・３週の木曜日
            if self.get_week_number(date) in TARGET_WEEKNUMBER:
                garbage = ''
            # それ以外の木曜日
            else:
                if self.get_week_number(date) == 5:
                    return ''
                garbage = ''
    
        # 金曜日の時
        elif date.weekday() == 4:
            garbage = ''
        else :
            return ''
    
        return garbage

def main():
    """
    １ヶ月のゴミ出しの予定をGoogleカレンダーに追加
    """
    creds = None
    # The file token.pickle stores the user's access and refresh tokens, and is
    # created automatically when the authorization flow completes for the first
    # time.
    if os.path.exists('token.pickle'):
        with open('token.pickle', 'rb') as token:
            creds = pickle.load(token)
    # If there are no (valid) credentials available, let the user log in.
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(
                'credentials.json', SCOPES)
            creds = flow.run_local_server(port=0)
        # Save the credentials for the next run
        with open('token.pickle', 'wb') as token:
            pickle.dump(creds, token)

    service = build('calendar', 'v3', credentials=creds)

    # Googleカレンダーに追加する予定
    today = datetime.datetime.now()
    for days in range(0,31):
        day = today + datetime.timedelta(days=days)

        if today.month != day.month:
            break
        
        g = garbage_schedule()

        summary = g.get_garbage_type(day)
        if summary == '':
            continue

        time = str(day.date())

        event = {
            'summary': summary, # 予定の見出し
            'start': { # 予定の開始時刻
                'dateTime': time+'T07:00:00',
                'timeZone': 'Japan',
            },
            'end': { # 予定の終了時刻
                'dateTime': time+'T08:30:00',
                'timeZone': 'Japan',
            },
        }
        event = service.events().insert(calendarId='your calendar id',body=event).execute()
        print (event['id'])

if __name__ == '__main__':
    main()