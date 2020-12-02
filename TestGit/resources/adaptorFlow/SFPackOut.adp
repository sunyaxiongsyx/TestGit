<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="SFPackOut"  location="out" flowType="adaptorFlow" description="存储转发接出流程-拆组包 ">
		<service id="StoreForwardSystemIdentifyService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="MonitorStep2BService" forcedExec="true" rule="" >
		</service>
		<service id="PackService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="default" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="UnpackService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="MonitorStep3BService" forcedExec="true" rule="" >
		</service>
		<attributes>
		</attributes>
		<parameters>
		</parameters>
	</flow>
</flows>