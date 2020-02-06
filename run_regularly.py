"""
    定期的にpythonを実行する環境を作る
"""
from crontab import CronTab

class CrontabControl:
    def __init__(self):
        self.cron = CronTab() # インスタンスの生成
        self.job = None
        self.all_job = None
    
    def write_job(self, com, schedule, file_name):
        # jobを生成して、実行コマンドを設定する
        self.job = self.cron.new(command = com) 
        self.job.setall(schedule)
        self.cron.write(file_name)

    def read_jobs(self, file_name):
        self.all_job = CronTab(tabfile=file_name)

    def monitor_start(self, file):
        self.read_jobs(file)
        for result in self.all_job.run_scheduler():
            print('予定していたスケジュールを実行しました')

def main():
    c = 'python garbage.py' # 実行コマンド
    schedule = '0 22 * * *' # '分　時　日　月　曜日'
    file = 'output.tab'

    control = CrontabControl()
    control.write_job(c, schedule, file)
    control.monitor_start(file)

if __name__ == '__main__':
    main()