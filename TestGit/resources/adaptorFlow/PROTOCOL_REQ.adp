<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="PROTOCOL_REQ"  location="in" flowType="protocolFlow" description="标准协议接入流程">
		<service id="ProtocolReadService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="UrlIdentifyService" forcedExec="false" rule="" >
		</service>
		<service id="FlowNoGenerateService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="TranshferResHeader" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<attributes>
		</attributes>
		<parameters>
		</parameters>
	</flow>
</flows>