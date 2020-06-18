import threading
import time
import typing
lock = threading.RLock()
def test():
    name = threading.current_thread().getName()
    lock.acquire()
    time.sleep(2)
    print('当前线程名称：',name)
    lock.release()

# threading.Thread(target=test).start()
# threading.Thread(target=test).start()


kw = {'city': 'Beijing', 'job': 'Engineer'}

def say(a):
    # typing.Match
    print("a:",a)
    print("city:",a.get('city'))

say(kw)