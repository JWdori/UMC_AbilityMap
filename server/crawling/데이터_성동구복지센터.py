#!/usr/bin/env python
# coding: utf-8

# In[1]:


get_ipython().system('pip3 install selenium')
get_ipython().system('pip3 install chromedriver_autoinstaller')
get_ipython().system('pip3 install BeautifulSoup4')


# In[34]:


import os
import time
import sys  

from selenium import webdriver 
from bs4 import BeautifulSoup
import chromedriver_autoinstaller
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains

import warnings
warnings.filterwarnings('ignore')

from selenium.common.exceptions import ElementNotInteractableException
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.keys import Keys


# In[35]:


#검색어 입력 
query = '성동구 복지센터'


# In[41]:


#chromedriver 연결
driver = webdriver.Chrome("./chromedriver")
driver.get(f"https://map.naver.com/v5/search/{query}?c=14203933.7141038,4562681.4505997,10,0,0,0,dh")


# In[42]:


driver.switch_to.frame("searchIframe")


# In[44]:


name_list = []
addr_list = []

try: 
    for i in range(1,7): 
        driver.find_element_by_link_text(str(i)).click()
        try: 
            for j in range(3,70,3):
                element = driver.find_elements_by_css_selector("._3Apve")[j] #_2s4DU - 성동구 복지센터
                ActionChains(driver).move_to_element(element).key_down(Keys.PAGE_DOWN).key_up(Keys.PAGE_DOWN).perform()
        except:
            pass

        title_raw = driver.find_elements_by_css_selector("._3Apve") 
        for title in title_raw:
            title = title.text
            name_list.append(title)

        # 평점 등 데이터
        data_raw = driver.find_elements_by_css_selector('._2Po-x') #_1h3B #1vSpB 
        #_2Po-x : 동까지 나옴
        #_3ZU00 : 상호명 중복
        #_3ZU00._1rBq3 : 개수가 안 맞음
        #_3LMxZ : 두배로 나옴
        #_2yqUQ : 안 나옴
        for data in data_raw: 
            data = data.text
            addr_list.append(data)
        
except:
    pass


print(len(name_list),len(addr_list))


# In[45]:


#print(name_list)
#print(addr_list)


# In[46]:


#print(name_list)
#print(addr_list)
get_ipython().system('pip3 install openpyxl')

import pandas as pd


# In[50]:


## 데이터 프레임 만들기
df = pd.DataFrame({'name':name_list, 'address':addr_list})
#df = pd.DataFrame({'title':title_list})
성동구 = df[:20]
print(성동구)


# In[53]:


#csv로 저장
import openpyxl

성동구.to_excel('성동구_복지센터.xlsx')


# In[54]:


info_list = []
    
try: 
    for i in range(1,7): 
        driver.find_element_by_link_text(str(i)).click()
        try: 
            for j in range(3,70,3):
                element = driver.find_elements_by_css_selector("._3Apve")[j] #_2s4DU - 성동구 복지센터
                ActionChains(driver).move_to_element(element).key_down(Keys.PAGE_DOWN).key_up(Keys.PAGE_DOWN).perform()
        except:
            pass

        data2_raw = driver.find_elements_by_css_selector('._3B6hV')
        for data in data2_raw:
            d = data.text
            info_list.append(d)
        
except:
    pass

print(len(info_list))


# In[55]:


#print(info_list)


# In[56]:


df = pd.DataFrame({'info':info_list})
성동구_info = df[:20]
print(성동구_info)


# In[23]:


#print(status_list)

