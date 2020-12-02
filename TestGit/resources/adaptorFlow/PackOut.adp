<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="PackOut"  location="out" flowType="adaptorFlow" description="拆组包接出流程">
		<service id="PackService" forcedExec="false" rule="" >
		</service>
		<service id="MonitorStep2BService" forcedExec="true" rule="" >
		</service>
		<service id="default" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="UnpackService" forcedExec="false" rule="" >
		</service>
		<service id="MonitorStep3BService" forcedExec="true" rule="" >
		</service>
		<attributes>
		</attributes>
		<parameters>
		</parameters>
	</flow>
</flows>