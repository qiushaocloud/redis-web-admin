# coding=utf-8
# _*_ encode:utf-8 _*_

import smtplib
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email.mime.multipart import MIMEMultipart
from email.header import Header
from email import encoders

from conf import logs
from conf.conf import mail_host, mail_user, mail_pass


def send_email(subject, text=None, files=None, receivers=None, is_html=False):
    """
    subject=邮件测试
    files=[file1,file2]
    text=内容
    mail=[mail1@mail.com,mail2@mail.com]
    """

    logs.debug("mail info: [user:{0},password:{1},host:{2},receivers:{3}]".format(
        mail_user, mail_pass, mail_host, receivers))

    message = MIMEMultipart()
    if is_html:
        message.attach(MIMEText(text, 'html', 'utf-8'))
    else:
        message.attach(MIMEText(text, 'plain', 'utf-8'))
    message['Subject'] = subject
    message['From'] = Header(mail_user)
    message['To'] = ','.join(receivers)

    if files is not None:
        for filename in files:
            if filename != '':
                with open(filename, 'rb') as f:
                    mime = MIMEBase('text', 'txt', filename=filename)
                    mime.add_header('Content-Disposition', 'attachment', filename=filename.split('/')[-1])
                    mime.set_payload(f.read())
                    encoders.encode_base64(mime)
                    message.attach(mime)

    try:
        smtpObj = smtplib.SMTP_SSL(timeout=5)
        smtpObj.connect(mail_host, 465)
        smtpObj.login(mail_user, mail_pass)
        smtpObj.sendmail(mail_user, receivers, message.as_string())
        smtpObj.quit()
        smtpObj.close()
        return True
    except smtplib.SMTPException as e:
        smtpObj.quit()
        smtpObj.close()
        logs.error(e)
        return False
