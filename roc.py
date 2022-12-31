# -*- coding: utf-8 -*-
"""
Created on Sat Jul 23 12:13:52 2022

@author: anmol
"""

import pandas as pd
import numpy as np

#import os
#print(os.listdir('.'))

#df = pd.read_csv(r'C:\Users\anmol\OneDrive\Desktop\one\Python Projects\qqqData.csv')
##print(df)

#df = pd.read_csv(r'C:\Users\anmol\OneDrive\Desktop\one\Python Projects\TQQQ.csv')

df = pd.read_csv(r'C:\Users\anmol\OneDrive\Desktop\one\Python Projects\SOXL.csv')
rocValues = None
rocList = None


def computeROC(dataFrame, period):
    
    #Define all preliminary variables
    rocCheck = False
    closeValues = df.Close
    x = period
    currDP = 0
    oldDP = None
    length = len(dataFrame.Close)
    
    #While currDP is < length of Close column, run commands
    while currDP <= length:
        currDP = closeValues[x]
        #print('currDP x: ' , x)
        oldDP = closeValues[x-period]
        
        #ROC formula
        roc = 100 * ((currDP-oldDP)/oldDP)
        #Assign roc to rocValues
        rocValues = roc
        
        #Append rocValues to list
        if (rocCheck == False):
            rocList = [rocValues]
            rocCheck = True
        else:
            rocList.append(rocValues)
            
        #print(roc)
        #print(rocList)
        x=x+1
        if x>=length:
            break
    
    return rocList

rocCompiled = computeROC(df, 9)
#print(rocCompiled)

        
def backTest(portfolioValue, dataFrame, period):
    i = 1
    holdStock = False
    numStock = None
    closeValues = df.Close
    lengthROC  = len(rocCompiled)
    while (i<= lengthROC):
        #Contingency to prevent out of bounds exceptions
        if (i >= lengthROC):
            break
        #If holding stock is true:
        if (holdStock):
            #If ROC is positive then hold on to stock
            if (rocCompiled[i] > -20):
                i = i + 1
                continue
            #If ROC is not positive then sell stock
            else:
                portfolioValue = (numStock * closeValues[i+period])
                #print(holdStock)
                i = i + 1
                holdStock = False
                print('sold stock at i: ', i, 'pValue: $', portfolioValue)
                i = i + 1
        #If holding stock is not true:
        else:
            #If ROC is <0, do not buy stock and change index by +1
            if (rocCompiled[i] < -20):
                i = i + 1
                continue
            #If ROC is > 0 and previous ROC index is < 0 then buy stock
            elif (rocCompiled[i] > -20 and rocCompiled[i-1] < -20):
                numStock = (portfolioValue/closeValues[i+period])
                holdStock = True
                print('bought stock at i: ', i, 'numStock: ', numStock)
                i = i + 1
            else: 
                i = i + 1
                continue
    print('final holdStock: ', holdStock)
    return portfolioValue
                
    
                
finalPortfolioValue = backTest(100000, df, 9)
print('Final Portfolio Value: $' , finalPortfolioValue)         
                    
    





